package com.bank.prueba.domain.service.impl;

import com.bank.prueba.domain.dto.CardDto;
import com.bank.prueba.domain.dto.request.ActivateCardRequest;
import com.bank.prueba.domain.dto.request.RechargeBalanceRequest;
import com.bank.prueba.domain.dto.response.CardResponse;
import com.bank.prueba.domain.exception.HttpGenericException;
import com.bank.prueba.domain.model.CardState;
import com.bank.prueba.domain.repository.ICardRepository;
import com.bank.prueba.domain.service.ICardService;
import com.bank.prueba.util.CardNumberGenerator;
import com.bank.prueba.util.TimeProvider;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.math.BigDecimal;

@Service
public class CardService implements ICardService {

    private final ICardRepository iCardRepository;
    private final CardNumberGenerator cardNumberGenerator;
    private final TimeProvider timeProvider;

    public CardService(ICardRepository iCardRepository, CardNumberGenerator cardNumberGenerator, TimeProvider timeProvider) {
        this.iCardRepository = iCardRepository;
        this.cardNumberGenerator = cardNumberGenerator;
        this.timeProvider = timeProvider;
    }

    @Override
    public CardDto getBalanceInquiry(String cardId) throws HttpGenericException {
            CardDto response = iCardRepository.getBalanceInquiry(cardId);
            if ( response == null || response.getId() == null) {
                throw new HttpGenericException(HttpStatus.NOT_FOUND, "No existe información");
            }
            return response;
    }

    @Override
    @Transactional
    public CardResponse postNumberCard(String productId) throws HttpGenericException {
        boolean existsCard = iCardRepository.existsByProductoId(productId);
        if (existsCard) throw new HttpGenericException(HttpStatus.CONFLICT,"Ya existe este número de tarjeta.");
        String numberCard = cardNumberGenerator.generate(productId);
        LocalDateTime now = timeProvider.now();
        LocalDateTime futureYear = now.plusYears(3);
        CardDto cardDto = new CardDto();
        cardDto.setProductoId(productId);
        cardDto.setNumeroTarjeta(numberCard);
        cardDto.setEstadoTarjeta(CardState.INACTIVE.getCode());
        cardDto.setFechaCreacion(now);
        cardDto.setSaldo(BigDecimal.ZERO);
        cardDto.setFechaVencimiento(futureYear);
        return iCardRepository.postNumberCard(cardDto);
    }

    @Override
    @Transactional
    public CardDto putActiveCard(ActivateCardRequest activateCardRequest) throws HttpGenericException {
        boolean existsCard = iCardRepository.existsByNumeroTarjeta(activateCardRequest.getCardId());
        CardDto response = iCardRepository.getBalanceInquiry(activateCardRequest.getCardId());
        if (!existsCard) throw new HttpGenericException(HttpStatus.NOT_FOUND,"No existe este número de tarjeta.");
        if ( response == null || CardState.fromCode(response.getEstadoTarjeta()) != CardState.INACTIVE ) throw new HttpGenericException(HttpStatus.NOT_ACCEPTABLE,"Ya fue activada previamente o estado inválido");
        iCardRepository.putActiveCard(activateCardRequest.getCardId(),CardState.ACTIVE.getCode());
        return iCardRepository.getBalanceInquiry(activateCardRequest.getCardId());
    }

    @Override
    @Transactional
    public String deleteFreezeCard(String cardId) throws HttpGenericException {
        boolean existsCard = iCardRepository.existsByNumeroTarjeta(cardId);
        CardDto response = iCardRepository.getBalanceInquiry(cardId);
        if (!existsCard) throw new HttpGenericException(HttpStatus.NOT_FOUND,"No existe este número de tarjeta.");
        if ( CardState.fromCode(response.getEstadoTarjeta()) == CardState.BLOCKED ) throw new HttpGenericException(HttpStatus.NOT_ACCEPTABLE,"Ya fue bloqueada previamente");
        iCardRepository.putActiveCard(cardId,CardState.BLOCKED.getCode());
        return "Su tarjeta con número: " + cardId + " fue bloqueada con éxito.";
    }

    @Override
    @Transactional
    public CardDto putRechargeBalance(RechargeBalanceRequest rechargeBalanceRequest) throws HttpGenericException {
        boolean existsCard = iCardRepository.existsByNumeroTarjeta(rechargeBalanceRequest.getCardId());
        if (!existsCard) throw new HttpGenericException(HttpStatus.NOT_FOUND,"No existe este número de tarjeta.");
        try {
            BigDecimal value = new BigDecimal(rechargeBalanceRequest.getBalance());
            if (value.signum() < 0) throw new NumberFormatException();
            // delegar a repositorio como string para compatibilidad con implementación existente
            return iCardRepository.putRechargeBalance(rechargeBalanceRequest.getCardId(), value.toString());
        } catch (NumberFormatException e) {
            throw new HttpGenericException(HttpStatus.CONFLICT,"Por favor solo ingresar números válidos para el saldo.");
        }
    }

}
