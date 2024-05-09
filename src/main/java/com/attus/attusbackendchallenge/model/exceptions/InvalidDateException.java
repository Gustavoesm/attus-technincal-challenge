package com.attus.attusbackendchallenge.model.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Invalid birthday, birthday must be between 1900's and today")
public class InvalidDateException extends InvalidFieldException {
    public InvalidDateException(String message) {
        super(message);
    }
}
