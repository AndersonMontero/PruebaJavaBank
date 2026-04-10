package com.bank.prueba.domain.model;

public enum CardState {
    ACTIVE(1),
    INACTIVE(2),
    BLOCKED(3);

    private final int code;

    CardState(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static CardState fromCode(Integer code) {
        if (code == null) return null;
        for (CardState s : values()) {
            if (s.code == code) return s;
        }
        return null;
    }
}
