package com.abakudev.retrypattern.retry;

public class RetryException extends RuntimeException{
    public RetryException(String message) {
        super(message);
    }
}
