package com.bank.prueba.domain.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionResponse {

    private String numeroTarjeta;

    private Integer id;

    private String saldoActual;

}
