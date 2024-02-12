package com.bank.prueba.domain.service;

import com.bank.prueba.domain.dto.TransactionDto;
import com.bank.prueba.domain.dto.request.AnulationRequest;
import com.bank.prueba.domain.dto.request.PurchaseRequest;
import com.bank.prueba.domain.dto.response.TransactionResponse;

public interface ITransactionService {

    String postPurchase(PurchaseRequest purchaseRequest);

    TransactionDto getTransaction(Integer transactionId);

    TransactionResponse putAnulation(AnulationRequest anulationRequest);

}
