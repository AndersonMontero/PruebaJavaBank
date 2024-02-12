package com.bank.prueba.domain.repository;

import com.bank.prueba.domain.dto.TransactionDto;

public interface ITransactionRepository {

    TransactionDto postPurchase(TransactionDto transactionDto);

    boolean existsByTransaction(Integer transactionId);

    TransactionDto getTransaction(Integer transactionId);

    void putAnulation(Integer transactionId, int estado);

}
