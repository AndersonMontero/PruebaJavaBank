package com.bank.prueba.domain.service;

import com.bank.prueba.domain.dto.CardDto;

public interface ICardService {
    CardDto getBalanceInquiry(Integer cardId);
}
