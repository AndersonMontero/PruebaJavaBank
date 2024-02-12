package com.bank.prueba.domain.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnulationRequest extends ActivateCardRequest {

    @NotNull(message = "El campo transactionId es obligatorio.")
    @Min(value = 1, message = "Tamaño minimo de transactionId es de 1.")
    private Integer transactionId;

}
