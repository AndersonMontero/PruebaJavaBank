package com.bank.prueba.domain.service.impl;

import com.bank.prueba.domain.dto.CardDto;
import com.bank.prueba.domain.dto.TransactionDto;
import com.bank.prueba.domain.dto.request.AnulationRequest;
import com.bank.prueba.domain.dto.request.PurchaseRequest;
import com.bank.prueba.domain.dto.response.TransactionResponse;
import com.bank.prueba.domain.exception.HttpGenericException;
import com.bank.prueba.domain.repository.ICardRepository;
import com.bank.prueba.domain.repository.ITransactionRepository;
import com.bank.prueba.domain.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    private ITransactionRepository iTransactionRepository;

    @Autowired
    private ICardRepository iCardRepository;

    @Override
    public String postPurchase(PurchaseRequest purchaseRequest) throws HttpGenericException {
        boolean existsCard = iCardRepository.existsByNumeroTarjeta(purchaseRequest.getCardId());
        if (!existsCard) throw new HttpGenericException(HttpStatus.CONFLICT,"No existe este número de tarjeta.");
        CardDto data = iCardRepository.getBalanceInquiry(purchaseRequest.getCardId());
        Integer saldo = Integer.valueOf(data.getSaldo());
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dateValidate = data.getFechaVencimiento();
        if (saldo != 0 && saldo >= purchaseRequest.getPrice()) throw new HttpGenericException(HttpStatus.CONFLICT,"Debe recargar la tarjeta actualmente tiene el saldo: "+ saldo);
        if (dateValidate.isAfter(now)) throw new HttpGenericException(HttpStatus.CONFLICT,"prueba validar fecha.");
        if (data.getEstadoTarjeta() != 2 && data.getEstadoTarjeta() != 3) throw new HttpGenericException(HttpStatus.CONFLICT,"prueba validar estado.");
        TransactionDto transactionDto;
        transactionDto = new TransactionDto();
        transactionDto.setIdTarjeta(data.getId());
        transactionDto.setFechaMovimiento(now);
        transactionDto.setMontoDinero(purchaseRequest.getPrice());
        transactionDto.setTipoOperacion(1);
        transactionDto.setEstadoMovimiento(1);
        Integer newSaldo = saldo-purchaseRequest.getPrice();
        if (newSaldo >= 0 ){
            iTransactionRepository.postPurchase(transactionDto);
            iCardRepository.putRechargeBalance(purchaseRequest.getCardId(),String.valueOf(newSaldo));
        } else {
            throw new HttpGenericException(HttpStatus.CONFLICT,"no se puede no tiene dinero");
        }
        return "Se creo";
    }

    @Override
    public TransactionDto getTransaction(Integer transactionId) throws HttpGenericException {
        boolean existsTransaction = iTransactionRepository.existsByTransaction(transactionId);
        if (existsTransaction) throw new HttpGenericException(HttpStatus.NOT_FOUND,"No existe nummero de Consultar transacción.");
        return iTransactionRepository.getTransaction(transactionId);
    }

    @Override
    public TransactionResponse putAnulation(AnulationRequest anulationRequest) throws HttpGenericException {
        boolean existsTransaction = iTransactionRepository.existsByTransaction(anulationRequest.getTransactionId());
        if (existsTransaction) throw new HttpGenericException(HttpStatus.NOT_FOUND,"No existe nummero de Consultar transacción.");
        TransactionDto validateData = iTransactionRepository.getTransaction(anulationRequest.getTransactionId());
        LocalDateTime date24hours = validateData.getFechaMovimiento().plusHours(24);
        if (validateData.getFechaMovimiento().isAfter(date24hours)) throw new HttpGenericException(HttpStatus.NOT_ACCEPTABLE,"La transacción a anular no debe ser mayor a 24 horas.");
        CardDto data = iCardRepository.getBalanceInquiry(anulationRequest.getCardId());
        Integer saldo = Integer.valueOf(data.getSaldo());
        Integer newSaldo = saldo+validateData.getMontoDinero();
        iCardRepository.putRechargeBalance(anulationRequest.getCardId(),String.valueOf(newSaldo));
        iTransactionRepository.putAnulation(anulationRequest.getTransactionId(),2);
        TransactionResponse transactionResponse;
        transactionResponse = new TransactionResponse();
        transactionResponse.setId(validateData.getId());
        transactionResponse.setNumeroTarjeta(anulationRequest.getCardId());
        transactionResponse.setSaldoActual(String.valueOf(newSaldo));
        return transactionResponse;
    }

}
