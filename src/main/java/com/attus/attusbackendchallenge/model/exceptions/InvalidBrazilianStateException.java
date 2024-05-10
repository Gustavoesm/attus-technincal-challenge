package com.attus.attusbackendchallenge.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid brazilian state")
public class InvalidBrazilianStateException extends InvalidFieldException {
    public InvalidBrazilianStateException(String message) {
        super(message);
    }
}
