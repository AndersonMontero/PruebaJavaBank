package com.bank.prueba.domain.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseRequest extends ActivateCardRequest{

    @NotNull(message = "El campo price es obligatorio.")
    @Min(value = 1, message = "Tamaño minimo de price es de 1 compra.")
    private Integer price;

}
