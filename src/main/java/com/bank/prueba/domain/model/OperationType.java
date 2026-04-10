package com.bank.prueba.domain.model;

public enum OperationType {
    PURCHASE(1),
    CANCELLATION(2);

    private final int code;

    OperationType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static OperationType fromCode(Integer code) {
        if (code == null) return null;
        for (OperationType t : values()) {
            if (t.code == code) return t;
        }
        return null;
    }
}
