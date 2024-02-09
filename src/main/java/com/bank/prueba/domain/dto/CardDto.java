package com.bank.prueba.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CardDto {

    private Integer id;

    private String numeroTarjeta;

    private String nombreTitular;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/yyyy")
    private Date fechaVencimiento;

    private Integer idTipoTarjeta;

    private String tipoTarjeta;

    private Integer estadoTarjeta;

    private Double saldo;

    private Date fechaCreacion;

}

