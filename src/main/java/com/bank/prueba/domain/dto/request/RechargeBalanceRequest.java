package com.bank.prueba.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RechargeBalanceRequest  extends ActivateCardRequest{

    @Size(min = 1, message = "Saldo minimo es de 1 dolar.")
    private String balance;

}
