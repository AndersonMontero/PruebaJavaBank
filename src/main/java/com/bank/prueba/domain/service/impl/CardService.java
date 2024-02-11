package com.bank.prueba.domain.service.impl;

import com.bank.prueba.domain.dto.CardDto;
import com.bank.prueba.domain.dto.request.ActivateCardRequest;
import com.bank.prueba.domain.dto.response.CardResponse;
import com.bank.prueba.domain.exception.HttpGenericException;
import com.bank.prueba.domain.repository.ICardRepository;
import com.bank.prueba.domain.service.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
        addData.setSaldo(0.0);
        addData.setFechaVencimiento(futureYear);
        return iCardRepository.postNumberCard(addData);
    }

    @Override
    public CardDto postActivateCard(ActivateCardRequest activateCardRequest) throws HttpGenericException {
        boolean existsCard = iCardRepository.existsByProductoId(activateCardRequest.getCardId());
        if (!existsCard) throw new HttpGenericException(HttpStatus.CONFLICT,"No existe este número de tarjeta.");
        CardDto addData;
        addData = new CardDto();
        addData.setEstadoTarjeta(1);
        return iCardRepository.postActivateCard(addData);
    }

    @Override
    public String deleteFreezeCard(String cardId) throws HttpGenericException {
        boolean existsCard = iCardRepository.existsByNumeroTarjeta(cardId);
        if (!existsCard) throw new HttpGenericException(HttpStatus.CONFLICT,"No existe este número de tarjeta.");
        CardDto addData;
        addData = new CardDto();
        addData.setEstadoTarjeta(2);
        iCardRepository.postActivateCard(addData);
        return "Su tarjeta con número: " + cardId + " fue Bloqueada con éxito.";
    }

}
