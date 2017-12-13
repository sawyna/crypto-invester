package com.pointy.assignment.common.exceptions;

public class CryptoClientException extends CryptoBaseException {

    public CryptoClientException(String message) {
        super(message);
    }

    public CryptoClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
