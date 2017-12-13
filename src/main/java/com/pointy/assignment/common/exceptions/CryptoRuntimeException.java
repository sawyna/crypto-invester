package com.pointy.assignment.common.exceptions;

public class CryptoRuntimeException extends RuntimeException {

    public CryptoRuntimeException(String message) {
        super(message);
    }

    public CryptoRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CryptoRuntimeException(Throwable cause) {
        super(cause);
    }
}
