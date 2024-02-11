package com.bank.prueba.persistence.crud;

import com.bank.prueba.domain.dto.CardDto;
import com.bank.prueba.persistence.entity.CardEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CardCrudRepository extends CrudRepository<CardEntity,Integer> {

    boolean existsByProductoId(String productId);

    CardEntity findByNumeroTarjeta(String cardId);

    boolean existsByNumeroTarjeta(String cardId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tarjeta SET estado_tarjeta = :i WHERE numero_tarjeta = :cardId", nativeQuery = true)
    void putActiveCard(@Param("cardId") String cardId, @Param("i") int i);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tarjeta SET saldo = :balance WHERE numero_tarjeta = :cardId", nativeQuery = true)
    void putRechargeBalance(@Param("cardId") String cardId, @Param("balance") String balance);

}
