package com.attus.attusbackendchallenge.model.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Invalid city name")
public class InvalidCityException extends InvalidFieldException {
    public InvalidCityException(String message) {
        super(message);
    }
}
