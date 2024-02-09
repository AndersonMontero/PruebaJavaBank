package com.bank.prueba.domain.service.impl;

import com.bank.prueba.domain.dto.CardDto;
import com.bank.prueba.domain.exception.HttpGenericException;
import com.bank.prueba.domain.repository.ICardRepository;
import com.bank.prueba.domain.service.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CardService implements ICardService {

    @Autowired
    private ICardRepository iCardRepository;

    @Override
    public CardDto getBalanceInquiry(String cardId) throws HttpGenericException {
        try {
            if(cardId.equals(null)){
                throw new HttpGenericException(HttpStatus.BAD_REQUEST,"Por favor ingrese un numero de tarjeta");
            }
            CardDto dato = iCardRepository.getBalanceInquiry(cardId);
            return dato;
        } catch (HttpGenericException e){
            throw e;
        }
    }
}
