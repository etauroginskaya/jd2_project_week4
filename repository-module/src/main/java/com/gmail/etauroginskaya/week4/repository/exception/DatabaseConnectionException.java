package com.gmail.etauroginskaya.week4.repository.exception;

public class DatabaseConnectionException extends RuntimeException {

    public DatabaseConnectionException(String message, Throwable e) {
        super(message, e);
    }
}
