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
                throw new HttpGenericException(HttpStatus.NOT_FOUND, "No existe información");
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
        CardDto cardDto;
        cardDto = new CardDto();
        cardDto.setProductoId(productId);
        cardDto.setNumeroTarjeta(numberCard);
        cardDto.setEstadoTarjeta(2);
        cardDto.setFechaCreacion(now);
        cardDto.setSaldo(0.0);
        cardDto.setFechaVencimiento(futureYear);
        return iCardRepository.postNumberCard(cardDto);
    }

    @Override
    public CardDto putActiveCard(ActivateCardRequest activateCardRequest) throws HttpGenericException {
        boolean existsCard = iCardRepository.existsByProductoId(activateCardRequest.getCardId());
        CardDto response = iCardRepository.getBalanceInquiry(activateCardRequest.getCardId());
        if (existsCard) throw new HttpGenericException(HttpStatus.NOT_FOUND,"No existe este número de tarjeta.");
        if ( !response.getEstadoTarjeta().equals(2) ) throw new HttpGenericException(HttpStatus.NOT_ACCEPTABLE,"Ya fue activada previamente");
        iCardRepository.putActiveCard(activateCardRequest.getCardId(),1);
        return iCardRepository.getBalanceInquiry(activateCardRequest.getCardId());
    }

    @Override
    public String deleteFreezeCard(String cardId) throws HttpGenericException {
        boolean existsCard = iCardRepository.existsByNumeroTarjeta(cardId);
        CardDto response = iCardRepository.getBalanceInquiry(cardId);
        if (!existsCard) throw new HttpGenericException(HttpStatus.NOT_FOUND,"No existe este número de tarjeta.");
        if ( response.getEstadoTarjeta().equals(3) ) throw new HttpGenericException(HttpStatus.NOT_ACCEPTABLE,"Ya fue bloqueada previamente");
        iCardRepository.putActiveCard(cardId,3);
        return "Su tarjeta con número: " + cardId + " fue bloqueada con éxito.";
    }

    @Override
    public CardDto putRechargeBalance(RechargeBalanceRequest rechargeBalanceRequest) throws HttpGenericException {
        boolean existsCard = iCardRepository.existsByNumeroTarjeta(rechargeBalanceRequest.getCardId());
        if (!existsCard) throw new HttpGenericException(HttpStatus.NOT_FOUND,"No existe este número de tarjeta.");
        if (Pattern.matches("\\d", rechargeBalanceRequest.getBalance()))
            throw new HttpGenericException(HttpStatus.CONFLICT,"Por favor solo ingresar números para el saldo.");
        return iCardRepository.putRechargeBalance(rechargeBalanceRequest.getCardId(),rechargeBalanceRequest.getBalance());
    }

}
