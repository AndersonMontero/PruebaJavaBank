package com.bank.prueba.domain.service.impl;

import com.bank.prueba.domain.dto.CardDto;
import com.bank.prueba.domain.dto.TransactionDto;
import com.bank.prueba.domain.dto.request.AnulationRequest;
import com.bank.prueba.domain.dto.request.PurchaseRequest;
import com.bank.prueba.domain.dto.response.TransactionResponse;
import com.bank.prueba.domain.exception.HttpGenericException;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private ITransactionRepository iTransactionRepository;

    @Mock
    private ICardRepository iCardRepository;

    @InjectMocks
    private TransactionService transactionService;

    private TransactionDto transactionDto;

    private CardDto cardDto;

    private PurchaseRequest purchaseRequest;

    @Test
    void testPostPurchaseFount() {
        purchaseRequest = new PurchaseRequest();
        purchaseRequest.setCardId("123");
        purchaseRequest.setPrice(150);

        CardDto cardDto = new CardDto();
        cardDto.setSaldo(100.0);

        when(iCardRepository.existsByNumeroTarjeta(purchaseRequest.getCardId())).thenReturn(true);
        when(iCardRepository.getBalanceInquiry(purchaseRequest.getCardId())).thenReturn(cardDto);

        assertThrows(HttpGenericException.class, () -> transactionService.postPurchase(purchaseRequest),
                "No tiene dinero suficiente para comprar.");

        verify(iCardRepository, times(1)).existsByNumeroTarjeta(purchaseRequest.getCardId());
        verify(iCardRepository, times(1)).getBalanceInquiry(purchaseRequest.getCardId());
        verify(iTransactionRepository, never()).postPurchase(any(TransactionDto.class));
        verify(iCardRepository, never()).putRechargeBalance(anyString(), anyString());
    }

    @Test
    void postPurchase() {
        LocalDateTime now = LocalDateTime.now();

        transactionDto = new TransactionDto();
        transactionDto.setIdTarjeta(1);
        transactionDto.setFechaMovimiento(now);
        transactionDto.setMontoDinero(15);
        transactionDto.setTipoOperacion(1);
        transactionDto.setEstadoMovimiento(1);

        purchaseRequest = new PurchaseRequest();
        purchaseRequest.setCardId("1234560123456789");
        purchaseRequest.setPrice(4);

        cardDto = new CardDto();
        cardDto.setSaldo(40.0);
        cardDto.setFechaVencimiento(now.plusYears(3));
        cardDto.setEstadoTarjeta(1);

        when(iCardRepository.existsByNumeroTarjeta(purchaseRequest.getCardId())).thenReturn(true);
        when(iCardRepository.getBalanceInquiry(purchaseRequest.getCardId())).thenReturn(cardDto);
        when(iTransactionRepository.postPurchase(any(TransactionDto.class))).thenReturn(transactionDto);
        when(iCardRepository.putRechargeBalance(eq(purchaseRequest.getCardId()), anyString())).thenReturn(cardDto);
        String result = null;
        try {
            result = transactionService.postPurchase(purchaseRequest);
        } catch (HttpGenericException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        assertNotNull(result);
        assertEquals("Se realizo la compra.", result);
        verify(iCardRepository, times(1)).existsByNumeroTarjeta(purchaseRequest.getCardId());
        verify(iCardRepository, times(1)).getBalanceInquiry(purchaseRequest.getCardId());
        verify(iTransactionRepository, times(1)).postPurchase(any(TransactionDto.class));
        verify(iCardRepository, times(1)).putRechargeBalance(eq(purchaseRequest.getCardId()), anyString());
    }

    @Test
    void testGetTransactionNotFound() {
        Integer transactionId = 1;

        when(iTransactionRepository.existsByTransaction(transactionId)).thenReturn(false);

        assertThrows(HttpGenericException.class, () -> transactionService.getTransaction(transactionId));

        verify(iTransactionRepository, times(1)).existsByTransaction(transactionId);
        verify(iTransactionRepository, never()).getTransaction(anyInt());
    }

    @Test
    void getTransaction() {
        transactionDto = new TransactionDto();
        transactionDto.setId(1);

        when(iTransactionRepository.existsByTransaction(transactionDto.getId())).thenReturn(true);
        when(iTransactionRepository.getTransaction(transactionDto.getId())).thenReturn(Optional.of(transactionDto));

        Optional<TransactionDto> result = null;
        try {
            result = transactionService.getTransaction(transactionDto.getId());
        } catch (HttpGenericException e) {
            fail("No hay datos: " + e.getMessage());
        }

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(transactionDto.getId(), result.get().getId());

        verify(iTransactionRepository, times(1)).existsByTransaction(transactionDto.getId());
        verify(iTransactionRepository, times(1)).getTransaction(transactionDto.getId());
    }

    @Test
    void putAnulation() {
        transactionDto = new TransactionDto();
        transactionDto.setId(1);
        transactionDto.setFechaMovimiento(LocalDateTime.now());
        transactionDto.setMontoDinero(50);

        AnulationRequest anulationRequest = new AnulationRequest();
        anulationRequest.setTransactionId(1);
        anulationRequest.setCardId("123456789");

        cardDto = new CardDto();
        cardDto.setSaldo(100.0);

        when(iTransactionRepository.existsByTransaction(anulationRequest.getTransactionId())).thenReturn(true);
        when(iTransactionRepository.getTransaction(anulationRequest.getTransactionId())).thenReturn(Optional.of(transactionDto));
        when(iCardRepository.getBalanceInquiry(anulationRequest.getCardId())).thenReturn(cardDto);

        TransactionResponse result = transactionService.putAnulation(anulationRequest);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(anulationRequest.getCardId(), result.getNumeroTarjeta());

        verify(iTransactionRepository, times(1)).existsByTransaction(anulationRequest.getTransactionId());
        verify(iTransactionRepository, times(1)).getTransaction(anulationRequest.getTransactionId());
        verify(iCardRepository, times(1)).getBalanceInquiry(anulationRequest.getCardId());
        verify(iCardRepository, times(1)).putRechargeBalance(anulationRequest.getCardId(), "150.0");
        verify(iTransactionRepository, times(1)).putAnulation(anulationRequest.getTransactionId(), 2);

    }

}