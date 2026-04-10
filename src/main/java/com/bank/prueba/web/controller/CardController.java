package com.bank.prueba.web.controller;

import com.bank.prueba.domain.dto.CardDto;
import com.bank.prueba.domain.dto.request.ActivateCardRequest;
import com.bank.prueba.domain.dto.request.RechargeBalanceRequest;
import com.bank.prueba.domain.dto.response.CardResponse;
import com.bank.prueba.domain.service.ICardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
@Validated
public class CardController {

    @Autowired
    private ICardService iCardService;

    @GetMapping("/{productId}/number")
    public ResponseEntity<?> postNumberCard(@PathVariable @Pattern(regexp = "\\d{6}", message = "Por favor ingrese el número de producto de 6 dígitos.") String productId) {
        CardResponse response = iCardService.postNumberCard(productId);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PostMapping("/enroll")
    public ResponseEntity<CardDto> putActiveCard(@RequestBody @Valid ActivateCardRequest activateCardRequest) {
        CardDto response = iCardService.putActiveCard(activateCardRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<?> deleteFreezeCard(@PathVariable @Pattern(regexp = "\\d{16}", message = "Por favor ingrese un numero de tarjeta válido.") String cardId) {
        String response = iCardService.deleteFreezeCard(cardId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/balance")
    public ResponseEntity<CardDto> putRechargeBalance(@RequestBody @Valid RechargeBalanceRequest rechargeBalanceRequest){
        CardDto response = iCardService.putRechargeBalance(rechargeBalanceRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/balance/{cardId}")
    public ResponseEntity<CardDto> getBalanceInquiry(@PathVariable @Pattern(regexp = "\\d{16}", message = "Por favor ingrese un numero de tarjeta válido.") String cardId) {
        CardDto response = iCardService.getBalanceInquiry(cardId);
        return ResponseEntity.ok(response);
    }

}
