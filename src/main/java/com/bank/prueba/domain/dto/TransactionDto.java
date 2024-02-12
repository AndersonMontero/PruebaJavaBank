package com.bank.prueba.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionDto {

    private Integer id;

    private Integer idTarjeta;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDateTime fechaMovimiento;

    private Integer montoDinero;

    private Integer tipoOperacion;

    private Integer estadoMovimiento;

}
