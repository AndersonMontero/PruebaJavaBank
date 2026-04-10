package com.bank.prueba.web.controller;

import com.bank.prueba.domain.dto.TransactionDto;
import com.bank.prueba.domain.dto.request.AnulationRequest;
import com.bank.prueba.domain.dto.request.PurchaseRequest;
import com.bank.prueba.domain.dto.response.TransactionResponse;
import com.bank.prueba.domain.service.ITransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/transaction")
@Validated
public class TransactionController {

    @Autowired
    private ITransactionService iTransactionService;

    @PostMapping("/purchase")
    public ResponseEntity<?> postPurchase(@RequestBody @Valid PurchaseRequest purchaseRequest) {
        String response = iTransactionService.postPurchase(purchaseRequest);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Optional<TransactionDto>> getTransaction(@PathVariable Integer transactionId){
        Optional<TransactionDto> response = iTransactionService.getTransaction(transactionId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/anulation")
    public ResponseEntity<TransactionResponse> putAnulation(@RequestBody @Valid AnulationRequest anulationRequest ){
        TransactionResponse response = iTransactionService.putAnulation(anulationRequest);
        return ResponseEntity.ok(response);
    }

}
