package com.bank.prueba.domain.repository;

import com.bank.prueba.domain.dto.TransactionDto;
import com.bank.prueba.persistence.entity.TransactionEntity;

import java.util.Optional;

public interface ITransactionRepository {

    TransactionDto postPurchase(TransactionDto transactionDto);

    boolean existsByTransaction(Integer transactionId);

    Optional<TransactionDto> getTransaction(Integer transactionId);

    void putAnulation(Integer transactionId, int estado);

}
