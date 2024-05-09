package com.attus.attusbackendchallenge.infra.exceptions;

import com.attus.attusbackendchallenge.model.exceptions.InvalidFieldException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Person is missing required fields")
public class InvalidPersonException extends InvalidFieldException {
    public InvalidPersonException(String message) {
        super(message);
    }
}
