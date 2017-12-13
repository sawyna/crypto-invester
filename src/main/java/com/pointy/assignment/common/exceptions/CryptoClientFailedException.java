package com.pointy.assignment.common.exceptions;

public class CryptoClientFailedException extends CryptoClientException {

    public CryptoClientFailedException(String message) {
        super(message);
    }

    public CryptoClientFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
