package com.bank.prueba.persistence;

import com.bank.prueba.domain.dto.CardDto;
import com.bank.prueba.domain.dto.response.CardResponse;
import com.bank.prueba.domain.repository.ICardRepository;
import com.bank.prueba.persistence.crud.CardCrudRepository;
import com.bank.prueba.persistence.entity.CardEntity;
import com.bank.prueba.persistence.mapper.CardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

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
        return cardMapper.toCardResponse(cardCrudRepository.save(cardEntity));
    }

    @Override
    public CardDto getBalanceInquiry(String cardId) {
        return cardMapper.toCardDto(cardCrudRepository.findByNumeroTarjeta(cardId));
    }

    @Override
    public void putActiveCard(String cardId, int estado) {
        cardCrudRepository.putActiveCard(cardId,estado);
    }

    @Override
    public CardDto putRechargeBalance(String cardId, String balance) {
        // convertir a BigDecimal para trabajar con el tipo monetario en la entidad
        BigDecimal value;
        try {
            value = new BigDecimal(balance);
        } catch (NumberFormatException e) {
            // si el formato no es válido, delegar al repositorio con cero para no romper la llamada (se recomienda validar antes)
            value = BigDecimal.ZERO;
        }
        cardCrudRepository.putRechargeBalance(cardId,value);
        return cardMapper.toCardDto(cardCrudRepository.findByNumeroTarjeta(cardId));
    }

    @Override
    public boolean existsByNumeroTarjeta(String cardId) {
        return cardCrudRepository.existsByNumeroTarjeta(cardId);
    }

}
