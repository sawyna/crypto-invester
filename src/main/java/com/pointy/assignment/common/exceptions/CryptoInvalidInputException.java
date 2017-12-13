package com.pointy.assignment.common.exceptions;

public class CryptoInvalidInputException extends CryptoBaseException {

    public CryptoInvalidInputException(String message) {
        super(message);
    }

    public CryptoInvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
