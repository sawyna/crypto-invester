package com.pointy.assignment.common.error;

public class Error {

    private String message;

    private String errorCode;

    private Error(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public static Error build(String message, String errorCode) {
        return new Error(message, errorCode);
    }
}
