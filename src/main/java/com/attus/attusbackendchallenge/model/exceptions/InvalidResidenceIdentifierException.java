package com.attus.attusbackendchallenge.model.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Invalid residence identifier")
public class InvalidResidenceIdentifierException extends InvalidFieldException {
    public InvalidResidenceIdentifierException(String message) {
        super(message);
    }
}
