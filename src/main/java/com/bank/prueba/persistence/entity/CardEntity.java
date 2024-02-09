package com.bank.prueba.persistence.entity;


import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tarjeta")
@Getter
@Setter
public class CardEntity {

    @Id
    private Integer id;

    @Column(name = "nombre_titular")
    private String nombreTitular;

    @Column(name = "fecha_vencimineto")
    private Date fechaVencimineto;

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
