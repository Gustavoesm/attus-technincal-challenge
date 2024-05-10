package com.attus.attusbackendchallenge.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Unable to retrieve main address for Person")
public class MainAddressNotFoundException extends RuntimeException {
    public MainAddressNotFoundException(String message) {
        super(message);
    }
}
