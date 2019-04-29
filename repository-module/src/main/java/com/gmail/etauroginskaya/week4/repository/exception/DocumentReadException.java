package com.gmail.etauroginskaya.week4.repository.exception;

public class DocumentReadException extends RuntimeException {

    public DocumentReadException(String message, Throwable e) {
        super(message, e);
    }
}
