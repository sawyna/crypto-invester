package com.pointy.assignment.common.exceptions;

/**
 * Base exception for all checked exceptions
 */
public class CryptoBaseException extends Exception {

    public CryptoBaseException(String message) {
        super(message);
    }

    public CryptoBaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
