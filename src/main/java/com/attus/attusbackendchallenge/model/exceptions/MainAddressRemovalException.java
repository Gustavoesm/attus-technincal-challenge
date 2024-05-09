package com.attus.attusbackendchallenge.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "You cannot remove a person's main address")
public class MainAddressRemovalException extends RuntimeException {
    public MainAddressRemovalException(String message) {
        super(message);
    }
}
