package com.brewery.app.exception;

/**
 * This class is responsible for throwing exception when an error occurred
 * while communicating with service
 */
public class ServiceException extends Exception {

    private final int errorCode;

    public ServiceException(final String message, final int errorCode, final Throwable throwable) {
        super(message, throwable);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}