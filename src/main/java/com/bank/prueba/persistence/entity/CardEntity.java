package com.bank.prueba.persistence.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "tarjeta")
@Getter
@Setter
public class CardEntity {

    @Id
    private Integer id;

    @Column(name = "numero_tarjeta")
    private String numeroTarjeta;

    @Column(name = "nombre_titular")
    private String nombreTitular;

    @Column(name = "fecha_vencimiento")
    private Date fechaVencimiento;

    @Column(name = "id_tipo_tarjeta")
    private Integer idTipoTarjeta;

    @Column(name = "tipo_tarjeta")
    private String tipoTarjeta;

    @Column(name = "estado_tarjeta")
    private Integer estadoTarjeta;

    private Double saldo;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

}
