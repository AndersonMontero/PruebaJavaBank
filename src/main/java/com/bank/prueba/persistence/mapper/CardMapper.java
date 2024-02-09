package com.bank.prueba.persistence.mapper;

import com.bank.prueba.domain.dto.CardDto;
import com.bank.prueba.persistence.entity.CardEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardMapper {
    CardDto toCardDto(CardEntity cardEntity);

}
