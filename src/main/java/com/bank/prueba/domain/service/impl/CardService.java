package com.bank.prueba.domain.service.impl;

import com.bank.prueba.domain.dto.CardDto;
import com.bank.prueba.domain.repository.ICardRepository;
import com.bank.prueba.domain.service.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService implements ICardService {

    @Autowired
    private ICardRepository iCardRepository;

    @Override
    public CardDto getBalanceInquiry(Integer cardId) {

        CardDto dato = iCardRepository.getBalanceInquiry(cardId);
/*
        CardDto data = new CardDto();
        data.setId(1234);
        data.setNombreTitular("Anderson Montero");
        data.setEstadoTarjeta(1);
        data.setSaldo(1.214);

 */
        return dato;
    }
}
