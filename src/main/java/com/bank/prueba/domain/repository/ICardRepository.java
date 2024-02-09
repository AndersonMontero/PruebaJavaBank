package com.bank.prueba.domain.repository;

import com.bank.prueba.domain.dto.CardDto;
import com.bank.prueba.domain.dto.response.CardResponse;

public interface ICardRepository {

    boolean existsByProductoId(String productId);

    CardResponse postNumberCard(CardDto addData);

    CardDto getBalanceInquiry(String cardId);

}
