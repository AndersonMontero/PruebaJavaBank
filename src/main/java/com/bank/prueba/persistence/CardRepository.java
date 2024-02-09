package com.bank.prueba.persistence;

import com.bank.prueba.domain.dto.CardDto;
import com.bank.prueba.domain.repository.ICardRepository;
import com.bank.prueba.persistence.crud.CardCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CardRepository implements ICardRepository {

    @Autowired
    private CardCrudRepository cardCrudRepository;

    @Override
    public CardDto getBalanceInquiry(Integer cardId) {
        CardDto data = new CardDto();
        data.setId(1234);
        data.setNombreTitular("Anderson Montero");
        data.setEstadoTarjeta(1);
        data.setSaldo(1.214);
        return data;
    }
}
