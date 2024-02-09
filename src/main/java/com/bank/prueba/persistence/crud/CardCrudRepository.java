package com.bank.prueba.persistence.crud;

import com.bank.prueba.persistence.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface CardCrudRepository extends CrudRepository<CardEntity,Integer> {
}
