package com.attus.attusbackendchallenge.model.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Invalid CEP")
public class InvalidCEPException extends InvalidFieldException {
    public InvalidCEPException(String message) {
        super(message);
    }
}
