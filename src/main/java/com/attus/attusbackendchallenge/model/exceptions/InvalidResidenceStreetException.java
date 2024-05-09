package com.attus.attusbackendchallenge.model.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Invalid street name")
public class InvalidResidenceStreetException extends InvalidFieldException {
    public InvalidResidenceStreetException(String message) {
        super(message);
    }
}
