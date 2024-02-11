package com.bank.prueba.domain.service;

import com.bank.prueba.domain.dto.CardDto;
import com.bank.prueba.domain.dto.request.ActivateCardRequest;
import com.bank.prueba.domain.dto.request.RechargeBalanceRequest;
import com.bank.prueba.domain.dto.response.CardResponse;

public interface ICardService {

    CardDto getBalanceInquiry(String cardId);

    CardResponse postNumberCard(String productId);

    CardDto postActivateCard(ActivateCardRequest activateCardRequest);

    String deleteFreezeCard(String cardId);

    CardDto postRechargeBalance(RechargeBalanceRequest rechargeBalanceRequest);


}
