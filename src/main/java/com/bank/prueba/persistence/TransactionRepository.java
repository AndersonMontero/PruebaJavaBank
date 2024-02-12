package com.bank.prueba.persistence;

import com.bank.prueba.domain.dto.TransactionDto;
import com.bank.prueba.domain.repository.ITransactionRepository;
import com.bank.prueba.persistence.crud.TransactionCrudRepository;
import com.bank.prueba.persistence.entity.TransactionEntity;
import com.bank.prueba.persistence.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepository implements ITransactionRepository {

    @Autowired
    private TransactionCrudRepository transactionCrudRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public TransactionDto postPurchase(TransactionDto transactionDto) {
        TransactionEntity transactionEntity = transactionMapper.toTransactionEntity(transactionDto);
        return transactionMapper.toTransactionDto(transactionCrudRepository.save(transactionEntity));
    }

    @Override
    public boolean existsByTransaction(Integer transactionId) {
        return transactionCrudRepository.existsById(transactionId);
    }

    @Override
    public TransactionDto getTransaction(Integer transactionId) {
        TransactionEntity transactionEntity = transactionMapper.toTransactionEntity(transactionId);
        return transactionMapper.toTransactionDto(transactionCrudRepository.findById(transactionEntity));
    }

    @Override
    public void putAnulation(Integer transactionId, int estado) {
        transactionCrudRepository.putAnulation(transactionId,estado);
    }

}
