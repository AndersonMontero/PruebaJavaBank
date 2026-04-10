package com.bank.prueba.persistence.mapper;

import com.bank.prueba.domain.dto.TransactionDto;
import com.bank.prueba.persistence.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionEntity toTransactionEntity(TransactionDto transactionDto);

    TransactionDto toTransactionDto(TransactionEntity save);

    @Mapping(target = "id", source = "transactionId")
    TransactionEntity toTransactionEntity(Integer transactionId);

}
