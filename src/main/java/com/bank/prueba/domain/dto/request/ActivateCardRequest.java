package com.bank.prueba.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ActivateCardRequest {

    @NotNull(message = "El campo cardId es obligatorio.")
    @Size(max = 16, message = "Tamaño máximo de número de cardId es de 16 digitos.")
    private String cardId;

}
