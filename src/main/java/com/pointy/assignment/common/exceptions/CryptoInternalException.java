package com.pointy.assignment.common.exceptions;

public class CryptoInternalException extends CryptoBaseException {

    public CryptoInternalException(String message) {
        super(message);
    }

    public CryptoInternalException(String message, Throwable cause) {
        super(message, cause);
    }
}
