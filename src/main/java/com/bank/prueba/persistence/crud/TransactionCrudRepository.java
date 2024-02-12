package com.bank.prueba.persistence.crud;

import com.bank.prueba.persistence.entity.TransactionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionCrudRepository extends JpaRepository<TransactionEntity,Integer> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE movimiento SET estado_movimiento = :estado WHERE id = :transactionId", nativeQuery = true)
    void putAnulation(@Param("transactionId") Integer transactionId, @Param("estado") int estado);

}
