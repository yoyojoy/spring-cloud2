package com.shengyecapital.common.exception;

public abstract class CustomException extends RuntimeException {

    public CustomException(String message, Throwable t) {
        super(message, t);
    }

    public CustomException(String message) {
        super(message);
    }

    public abstract String getErrorCode();

}
