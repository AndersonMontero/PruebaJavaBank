package com.bank.prueba.web.controller;

import com.bank.prueba.domain.dto.CardDto;
import com.bank.prueba.domain.service.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return "Hola Ingreso";
    }

    @GetMapping("/balance/{cardId}")
    public ResponseEntity<CardDto> getBalanceInquiry(@PathVariable("cardId") Integer cardId){
        CardDto response = iCardService.getBalanceInquiry(cardId);
        return ResponseEntity.ok(response);
    }


}
