package com.bank.prueba.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimiento")
@Getter
@Setter
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_tarjeta")
    private Integer idTarjeta;

    @Column(name = "fecha_movimiento")
    private LocalDateTime fechaMovimiento;

    @Column(name = "monto_dinero")
    private Integer montoDinero;

    @Column(name = "tipo_operacion")
    private Integer tipoOperacion;

    @Column(name = "estado_movimiento")
    private Integer estadoMovimiento;

}
