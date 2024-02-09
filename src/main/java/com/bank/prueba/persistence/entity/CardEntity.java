package com.bank.prueba.persistence.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tarjeta")
@Getter
@Setter
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_producto")
    private String productoId;

    @Column(name = "numero_tarjeta")
    private String numeroTarjeta;

    @Column(name = "nombre_titular")
    private String nombreTitular;

    @Column(name = "fecha_vencimiento")
    private LocalDateTime fechaVencimiento;

    @Column(name = "id_tipo_tarjeta")
    private Integer idTipoTarjeta;

    @Column(name = "tipo_tarjeta")
    private String tipoTarjeta;

    @Column(name = "estado_tarjeta")
    private Integer estadoTarjeta;

    private Double saldo;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

}
