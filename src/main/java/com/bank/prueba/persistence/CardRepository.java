package com.bank.prueba.persistence;

import com.bank.prueba.domain.dto.CardDto;
import com.bank.prueba.domain.repository.ICardRepository;
import com.bank.prueba.persistence.crud.CardCrudRepository;
import com.bank.prueba.persistence.mapper.CardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CardRepository implements ICardRepository {

    @Autowired
    private CardCrudRepository cardCrudRepository;

    @Autowired
    private CardMapper cardMapper;

    @Override
    public CardDto getBalanceInquiry(String cardId) {
        return cardMapper.toCardDto(cardCrudRepository.findByNumeroTarjeta(cardId));
    }
}
