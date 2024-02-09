package com.bank.prueba.domain.repository;

import com.bank.prueba.domain.dto.CardDto;

public interface ICardRepository {
    CardDto getBalanceInquiry(Integer cardId);
}
