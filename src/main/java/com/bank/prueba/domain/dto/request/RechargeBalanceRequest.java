package com.bank.prueba.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RechargeBalanceRequest  extends ActivateCardRequest{

    @NotNull(message = "El cardId es obligatorio")
    @Pattern(regexp = "\\d{16}", message = "El número de tarjeta debe tener 16 dígitos")
    private String cardId;

    @NotBlank(message = "El balance es obligatorio")
    @Pattern(regexp = "^\\d+(\\\\.\\d{1,2})?$", message = "El balance debe ser un número válido (hasta 2 decimales)")
    @Size(min = 1, message = "Saldo minimo es de 1 dolar.")
    private String balance;

}
