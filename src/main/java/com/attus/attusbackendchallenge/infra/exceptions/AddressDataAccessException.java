package com.attus.attusbackendchallenge.infra.exceptions;

import org.springframework.dao.DataAccessException;

public class AddressDataAccessException extends DataAccessException {
    public AddressDataAccessException(String msg) {
        super(msg);
    }

    public AddressDataAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
