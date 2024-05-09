package com.attus.attusbackendchallenge.model.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Invalid person name")
public class InvalidPersonNameException extends InvalidFieldException {
    public InvalidPersonNameException(String message) {
        super(message);
    }
}
