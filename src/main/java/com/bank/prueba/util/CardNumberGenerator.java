package com.bank.prueba.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class CardNumberGenerator {

    private final SecureRandom random = new SecureRandom();

    public String generate(String productId) {
        long part = (random.nextLong() & Long.MAX_VALUE) % 1_000_000_0000L; // 0 .. 9_999_999_999
        String randomPartStr = String.format("%010d", part);
        String number = productId + randomPartStr;
        if (number.length() != 16) {
            throw new IllegalArgumentException("Error generando número de tarjeta");
        }
        return number;
    }
}
