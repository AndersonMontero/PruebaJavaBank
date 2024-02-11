package com.bank.prueba.persistence;

import com.bank.prueba.domain.dto.CardDto;
import com.bank.prueba.domain.dto.response.CardResponse;
import com.bank.prueba.domain.repository.ICardRepository;
import com.bank.prueba.persistence.crud.CardCrudRepository;
import com.bank.prueba.persistence.entity.CardEntity;
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
    public boolean existsByProductoId(String productId) {
        return cardCrudRepository.existsByProductoId(productId);
    }

    @Override
    public CardResponse postNumberCard(CardDto addData) {
        CardEntity cardEntity = cardMapper.toCardEntity(addData);
        return cardMapper.toCarResponse(cardCrudRepository.save(cardEntity));
    }

    @Override
    public CardDto getBalanceInquiry(String cardId) {
        return cardMapper.toCardDto(cardCrudRepository.findByNumeroTarjeta(cardId));
    }

    @Override
    public CardDto postActivateCard(CardDto addData) {
        CardEntity cardEntity = cardMapper.toCardEntity(addData);
        return cardMapper.toCardDto(cardCrudRepository.save(cardEntity));
    }

    @Override
    public boolean existsByNumeroTarjeta(String cardId) {
        return cardCrudRepository.existsByNumeroTarjeta(cardId);
    }

}
