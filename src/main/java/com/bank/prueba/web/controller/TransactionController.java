package com.bank.prueba.web.controller;

import com.bank.prueba.domain.dto.TransactionDto;
import com.bank.prueba.domain.dto.request.ActivateCardRequest;
import com.bank.prueba.domain.dto.request.AnulationRequest;
import com.bank.prueba.domain.dto.request.PurchaseRequest;
import com.bank.prueba.domain.dto.response.TransactionResponse;
import com.bank.prueba.domain.exception.HttpGenericException;
import com.bank.prueba.domain.service.ITransactionService;
import jakarta.validation.Valid;
import org.mapstruct.ap.shaded.freemarker.template.utility.HtmlEscape;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private ITransactionService iTransactionService;

    @PostMapping("/purchase")
    public ResponseEntity<?> postPurchase(@RequestBody @Valid PurchaseRequest purchaseRequest) {
        try {
            if (!Pattern.matches("\\d{16}", purchaseRequest.getCardId())){
                throw new HttpGenericException(HttpStatus.LENGTH_REQUIRED,"Por favor ingrese el número de tarjeta de 16 dígitos.");
            }
            String response = iTransactionService.postPurchase(purchaseRequest);
            return new ResponseEntity<>(response,HttpStatus.CREATED);
        }catch (HttpGenericException e){
            throw e;
        }
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDto> getTransaction(@PathVariable Integer transactionId){
        try {
            if (transactionId != null) throw new HttpGenericException(HttpStatus.BAD_GATEWAY,"por favor ingresar numero a consultar de transacción.");
            TransactionDto response = iTransactionService.getTransaction(transactionId);
            return ResponseEntity.ok(response);
        } catch (HttpGenericException e){
            throw e;
        }

    }

    @PutMapping("/anulation")
    public ResponseEntity<TransactionResponse> putAnulation(@RequestBody @Valid AnulationRequest anulationRequest ){
        try {
            if (anulationRequest.getTransactionId() != null && !Pattern.matches("\\d{16}", anulationRequest.getCardId())) throw new HttpGenericException(HttpStatus.BAD_GATEWAY,"por favor ingresar numero.");
            TransactionResponse response = iTransactionService.putAnulation(anulationRequest);
            return ResponseEntity.ok(response);
        }catch (HttpGenericException e){
            throw e;
        }
    }

}
