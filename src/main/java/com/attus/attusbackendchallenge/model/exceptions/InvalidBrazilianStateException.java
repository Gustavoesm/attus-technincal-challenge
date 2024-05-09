package com.attus.attusbackendchallenge.model.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Invalid brazilian state")
public class InvalidBrazilianStateException extends InvalidFieldException {
    public InvalidBrazilianStateException(String message) {
        super(message);
    }
}
