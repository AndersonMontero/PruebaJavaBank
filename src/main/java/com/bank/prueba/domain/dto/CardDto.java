package com.bank.prueba.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CardDto {

    private Integer id;

    private String productoId;

    private String numeroTarjeta;

    private String nombreTitular;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/yyyy")
    private LocalDateTime fechaVencimiento;

    private Integer idTipoTarjeta;

    private String tipoTarjeta;

    private Integer estadoTarjeta;

    private Double saldo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDateTime fechaCreacion;

}

