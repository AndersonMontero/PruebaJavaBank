package com.bank.prueba.domain.service.impl;

import com.bank.prueba.domain.dto.CardDto;
import com.bank.prueba.domain.dto.TransactionDto;
import com.bank.prueba.domain.dto.request.PurchaseRequest;
import com.bank.prueba.domain.repository.ICardRepository;
import com.bank.prueba.domain.repository.ITransactionRepository;
import com.bank.prueba.persistence.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private ITransactionRepository iTransactionRepository;

    @Mock
    private ICardRepository iCardRepository;

    @InjectMocks
    private TransactionService transactionService;
/*
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }
*/
    @Test
    void postPurchase() {
        LocalDateTime now = LocalDateTime.now();

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setIdTarjeta(1);
        transactionDto.setFechaMovimiento(now);
        transactionDto.setMontoDinero(15);
        transactionDto.setTipoOperacion(1);
        transactionDto.setEstadoMovimiento(1);

        PurchaseRequest purchaseRequest;
        purchaseRequest = new PurchaseRequest();
        purchaseRequest.setCardId("1234560123456789");
        purchaseRequest.setPrice(4);

        CardDto cardDto = new CardDto();
        cardDto.setSaldo(40.0);
        cardDto.setFechaVencimiento(now.plusYears(3));
        cardDto.setEstadoTarjeta(1);

        Mockito.when(iCardRepository.existsByNumeroTarjeta(any(String.class))).thenReturn(true);
        Mockito.when(iCardRepository.getBalanceInquiry(any(String.class))).thenReturn(cardDto);
        Mockito.when(iTransactionRepository.postPurchase(any(TransactionDto.class))).thenReturn(transactionDto);
        String result = transactionService.postPurchase(purchaseRequest);
        assertNotNull(result);
        assertEquals("Se realizo la compra.", result);
        verify(iTransactionRepository, times(1)).postPurchase(any(TransactionDto.class));
    }

    @Test
    void getTransaction() {
    }

    @Test
    void putAnulation() {
    }
}