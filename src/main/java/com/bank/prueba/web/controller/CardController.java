package com.bank.prueba.web.controller;

import com.bank.prueba.domain.dto.CardDto;
import com.bank.prueba.domain.dto.request.ActivateCardRequest;
import com.bank.prueba.domain.dto.response.CardResponse;
import com.bank.prueba.domain.exception.HttpGenericException;
import com.bank.prueba.domain.service.ICardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    private ICardService iCardService;

    @GetMapping("/prueba")
    public String prueba(){
        return "Hola Ingreso1234";
    }

    @PostMapping("/{productId}/number")
    public ResponseEntity<CardResponse> postNumberCard(@PathVariable String productId) {
        try {
            if (!Pattern.matches("\\d{6}", productId)){
                throw new HttpGenericException(HttpStatus.LENGTH_REQUIRED,"Por favor ingrese el número de producto de 6 dígitos.");
            }
            CardResponse response = iCardService.postNumberCard(productId);
            return ResponseEntity.ok(response);
        } catch (HttpGenericException e){
            throw e;
        }
    }

    @PostMapping("/enroll")
    public ResponseEntity<CardDto> postActivateCard(@RequestBody @Valid ActivateCardRequest activateCardRequest) {
        try {
            if (!Pattern.matches("\\d{6}", activateCardRequest.getCardId())){
                throw new HttpGenericException(HttpStatus.LENGTH_REQUIRED,"Por favor ingrese el número de producto de 6 dígitos.");
            }
            CardDto response = iCardService.postActivateCard(activateCardRequest);
            return ResponseEntity.ok(response);
        } catch (HttpGenericException e){
            throw e;
        }
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<String> deleteFreezeCard(@PathVariable String cardId) {
        try {
            if (cardId == null || cardId.length() != 16) {
                throw new HttpGenericException(HttpStatus.LENGTH_REQUIRED, "Por favor ingrese un numero de tarjeta válido.");
            }
            String response = iCardService.deleteFreezeCard(cardId);
            return ResponseEntity.ok(response);
        }catch (HttpGenericException e){
            throw e;
        }
    }

    @GetMapping("/balance/{cardId}")
    public ResponseEntity<CardDto> getBalanceInquiry(@PathVariable String cardId) {
        try {
            if (cardId == null || cardId.length() != 16) {
                throw new HttpGenericException(HttpStatus.LENGTH_REQUIRED, "Por favor ingrese un numero de tarjeta válido.");
            }
            CardDto response = iCardService.getBalanceInquiry(cardId);
            return ResponseEntity.ok(response);
        } catch (HttpGenericException e){
            throw e;
        }
    }

}
