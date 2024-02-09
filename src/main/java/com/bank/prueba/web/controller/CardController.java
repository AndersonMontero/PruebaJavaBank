package com.bank.prueba.web.controller;

import com.bank.prueba.domain.dto.CardDto;
import com.bank.prueba.domain.exception.HttpGenericException;
import com.bank.prueba.domain.service.ICardService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    private ICardService iCardService;

    @GetMapping("/prueba")
    public String prueba(){
        return "Hola Ingreso1234";
    }

    @GetMapping("/balance/{cardId}")
    public ResponseEntity<CardDto> getBalanceInquiry(@PathVariable String cardId) {
        try {
            if (cardId == null || cardId.length() != 16) {
                throw new HttpGenericException(HttpStatus.LENGTH_REQUIRED, "Por favor ingrese un numero de tarjeta válido");
            }
            CardDto response = iCardService.getBalanceInquiry(cardId);
            return ResponseEntity.ok(response);
        } catch (HttpGenericException e){
            throw e;
        }
    }


}
