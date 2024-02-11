package com.bank.prueba.persistence.crud;

import com.bank.prueba.persistence.entity.CardEntity;
import org.springframework.data.repository.CrudRepository;

public interface CardCrudRepository extends CrudRepository<CardEntity,Integer> {

    boolean existsByProductoId(String productId);

    CardEntity findByNumeroTarjeta(String cardId);

    boolean existsByNumeroTarjeta(String cardId);

}
