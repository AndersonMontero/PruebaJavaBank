package com.bank.prueba.domain.service.impl;

import com.bank.prueba.domain.dto.CardDto;
import com.bank.prueba.domain.dto.request.ActivateCardRequest;
import com.bank.prueba.domain.dto.request.RechargeBalanceRequest;
import com.bank.prueba.domain.dto.response.CardResponse;
import com.bank.prueba.domain.exception.HttpGenericException;
import com.bank.prueba.domain.repository.ICardRepository;
import com.bank.prueba.domain.service.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Service
public class CardService implements ICardService {

    @Autowired
    private ICardRepository iCardRepository;

    @Override
    public CardDto getBalanceInquiry(String cardId) throws HttpGenericException {
            CardDto response = iCardRepository.getBalanceInquiry(cardId);

            if (response == null || response.getId() == null) {
                throw new HttpGenericException(HttpStatus.INTERNAL_SERVER_ERROR, "No existe información");
            }
            return response;
    }

    @Override
    public CardResponse postNumberCard(String productId) throws HttpGenericException {
        boolean existsCard = iCardRepository.existsByProductoId(productId);
        if (existsCard) throw new HttpGenericException(HttpStatus.CONFLICT,"Ya existe este número de tarjeta.");
        Long randomNumber = (long) (Math.random() * 9_999_999_9) + 1_000_000_000;
        String numberCard = productId.concat(randomNumber.toString());
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime futureYear = now.plusYears(3);
        CardDto addData;
        addData = new CardDto();
        addData.setProductoId(productId);
        addData.setNumeroTarjeta(numberCard);
        addData.setEstadoTarjeta(2);
        addData.setFechaCreacion(now);
        addData.setSaldo("0");
        addData.setFechaVencimiento(futureYear);
        return iCardRepository.postNumberCard(addData);
    }

    @Override
    public CardDto putActiveCard(ActivateCardRequest activateCardRequest) throws HttpGenericException {
        boolean existsCard = iCardRepository.existsByProductoId(activateCardRequest.getCardId());
        if (existsCard) throw new HttpGenericException(HttpStatus.CONFLICT,"No existe este número de tarjeta.");
        return iCardRepository.putActiveCard(activateCardRequest.getCardId(),1);
    }

    @Override
    public String deleteFreezeCard(String cardId) throws HttpGenericException {
        boolean existsCard = iCardRepository.existsByNumeroTarjeta(cardId);
        if (existsCard) throw new HttpGenericException(HttpStatus.CONFLICT,"No existe este número de tarjeta.");
        iCardRepository.putActiveCard(cardId,2);
        return "Su tarjeta con número: " + cardId + " fue bloqueada con éxito.";
    }

    @Override
    public CardDto putRechargeBalance(RechargeBalanceRequest rechargeBalanceRequest) {
        boolean existsCard = iCardRepository.existsByNumeroTarjeta(rechargeBalanceRequest.getCardId());
        if (!existsCard) throw new HttpGenericException(HttpStatus.CONFLICT,"No existe este número de tarjeta.");
        if (Pattern.matches("\\d", rechargeBalanceRequest.getBalance()))
            throw new HttpGenericException(HttpStatus.CONFLICT,"Por favor solo ingresar números para el saldo.");
        return iCardRepository.putRechargeBalance(rechargeBalanceRequest.getCardId(),rechargeBalanceRequest.getBalance());
    }

}
