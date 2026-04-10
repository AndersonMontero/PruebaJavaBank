package com.bank.prueba.domain.service.impl;

import com.bank.prueba.domain.dto.CardDto;
import com.bank.prueba.domain.dto.TransactionDto;
import com.bank.prueba.domain.dto.request.AnulationRequest;
import com.bank.prueba.domain.dto.request.PurchaseRequest;
import com.bank.prueba.domain.dto.response.TransactionResponse;
import com.bank.prueba.domain.exception.HttpGenericException;
import com.bank.prueba.domain.model.CardState;
import com.bank.prueba.domain.model.OperationType;
import com.bank.prueba.domain.repository.ICardRepository;
import com.bank.prueba.domain.repository.ITransactionRepository;
import com.bank.prueba.domain.service.ITransactionService;
import com.bank.prueba.util.TimeProvider;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.math.BigDecimal;

@Service
public class TransactionService implements ITransactionService {

    private final ITransactionRepository iTransactionRepository;
    private final ICardRepository iCardRepository;
    private final TimeProvider timeProvider;

    public TransactionService(ITransactionRepository iTransactionRepository, ICardRepository iCardRepository, TimeProvider timeProvider) {
        this.iTransactionRepository = iTransactionRepository;
        this.iCardRepository = iCardRepository;
        this.timeProvider = timeProvider;
    }

    @Override
    @Transactional
    public String postPurchase(PurchaseRequest purchaseRequest) throws HttpGenericException {
        boolean existsCard = iCardRepository.existsByNumeroTarjeta(purchaseRequest.getCardId());
        if (!existsCard) throw new HttpGenericException(HttpStatus.CONFLICT,"No existe este número de tarjeta.");
        CardDto data = iCardRepository.getBalanceInquiry(purchaseRequest.getCardId());
        BigDecimal saldo = data.getSaldo() == null ? BigDecimal.ZERO : data.getSaldo();
        LocalDateTime now = timeProvider.now();
        LocalDateTime dateValidate = data.getFechaVencimiento();
        // Verificar estado de tarjeta: 2=inactiva, 3=bloqueada
        if (data.getEstadoTarjeta() != null && (CardState.fromCode(data.getEstadoTarjeta()) == CardState.INACTIVE || CardState.fromCode(data.getEstadoTarjeta()) == CardState.BLOCKED)) {
            throw new HttpGenericException(HttpStatus.CONFLICT,"La tarjeta está inactiva o bloqueada. No se puede realizar la compra.");
        }
        // Verificar vencimiento
        if (dateValidate != null && now.isAfter(dateValidate)) throw new HttpGenericException(HttpStatus.CONFLICT,"La tarjeta no está vigente, la fecha de la transacción es posterior a la fecha de vencimiento.");
        BigDecimal price = BigDecimal.valueOf(purchaseRequest.getPrice());
        // Verificar saldo suficiente
        if (saldo.compareTo(price) < 0) throw new HttpGenericException(HttpStatus.CONFLICT,"No tiene dinero para comprar.");

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setIdTarjeta(data.getId());
        transactionDto.setFechaMovimiento(now);
        transactionDto.setMontoDinero(price);
        transactionDto.setTipoOperacion(OperationType.PURCHASE.getCode());
        transactionDto.setEstadoMovimiento(1);
        BigDecimal newSaldo = saldo.subtract(price);
        if (newSaldo.compareTo(BigDecimal.ZERO) >= 0 ){
            iTransactionRepository.postPurchase(transactionDto);
            iCardRepository.putRechargeBalance(purchaseRequest.getCardId(),newSaldo.toString());
        } else {
            throw new HttpGenericException(HttpStatus.CONFLICT,"No tiene dinero para comprar.");
        }
        return "Se realizo la compra.";
    }

    @Override
    public Optional<TransactionDto> getTransaction(Integer transactionId) throws HttpGenericException {
        boolean existsTransaction = iTransactionRepository.existsByTransaction(transactionId);
        if (!existsTransaction) throw new HttpGenericException(HttpStatus.NOT_FOUND,"No existe nummero de transacción.");
        return iTransactionRepository.getTransaction(transactionId);
    }

    @Override
    @Transactional
    public TransactionResponse putAnulation(AnulationRequest anulationRequest) throws HttpGenericException {
        boolean existsTransaction = iTransactionRepository.existsByTransaction(anulationRequest.getTransactionId());
        if (!existsTransaction) throw new HttpGenericException(HttpStatus.NOT_FOUND,"No existe nummero de Consultar transacción.");
        Optional<TransactionDto> validateData = iTransactionRepository.getTransaction(anulationRequest.getTransactionId());
        if (!validateData.isPresent()) throw new HttpGenericException(HttpStatus.NOT_FOUND, "No existe la transacción solicitada.");
        LocalDateTime movimiento = validateData.get().getFechaMovimiento();
        LocalDateTime date24hours = movimiento.plusHours(24);
        // Si la fecha actual es posterior a movimiento+24h, no se puede anular
        if (timeProvider.now().isAfter(date24hours)) throw new HttpGenericException(HttpStatus.NOT_ACCEPTABLE,"La transacción a anular no debe ser mayor a 24 horas.");
        // Obtener tarjeta y actualizar saldo
        CardDto data = iCardRepository.getBalanceInquiry(anulationRequest.getCardId());
        BigDecimal saldo = data.getSaldo() == null ? BigDecimal.ZERO : data.getSaldo();
        BigDecimal newSaldo = saldo.add(validateData.get().getMontoDinero());
        iCardRepository.putRechargeBalance(anulationRequest.getCardId(),newSaldo.toString());
        iTransactionRepository.putAnulation(anulationRequest.getTransactionId(),OperationType.CANCELLATION.getCode());
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setId(validateData.get().getId());
        transactionResponse.setNumeroTarjeta(anulationRequest.getCardId());
        transactionResponse.setSaldoActual(newSaldo.toString());
        return transactionResponse;
    }

}
