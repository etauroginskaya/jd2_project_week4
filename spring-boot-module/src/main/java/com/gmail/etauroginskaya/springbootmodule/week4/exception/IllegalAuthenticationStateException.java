package com.gmail.etauroginskaya.springbootmodule.week4.exception;

public class IllegalAuthenticationStateException extends RuntimeException {
    public IllegalAuthenticationStateException(String message) {
        super(message);
    }
}
