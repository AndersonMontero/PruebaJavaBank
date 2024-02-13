package com.bank.prueba.domain.service;

import com.bank.prueba.domain.dto.TransactionDto;
import com.bank.prueba.domain.dto.request.AnulationRequest;
import com.bank.prueba.domain.dto.request.PurchaseRequest;
import com.bank.prueba.domain.dto.response.TransactionResponse;
import com.bank.prueba.persistence.entity.TransactionEntity;

import java.util.Optional;

public interface ITransactionService {

    String postPurchase(PurchaseRequest purchaseRequest);

    Optional<TransactionDto> getTransaction(Integer transactionId);

    TransactionResponse putAnulation(AnulationRequest anulationRequest);

}
