package com.attus.attusbackendchallenge.infra.exceptions;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(String message) {
        super(message);
    }

    public PersonNotFoundException(String message, Exception e) {
        super(message, e);
    }
}
