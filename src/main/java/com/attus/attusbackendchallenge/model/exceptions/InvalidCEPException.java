package com.attus.attusbackendchallenge.model.exceptions;

public class InvalidCEPException extends RuntimeException {
    public InvalidCEPException(String message) {
        super(message);
    }
}
