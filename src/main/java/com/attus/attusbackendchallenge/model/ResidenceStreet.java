package com.attus.attusbackendchallenge.model;

import com.attus.attusbackendchallenge.model.exceptions.InvalidResidenceStreetException;

public record ResidenceStreet(String name) {
    private static final int MAX_LENGTH = 128;

    public ResidenceStreet {
        name = name.trim();
        if (!isValid(name)) {
            throw new InvalidResidenceStreetException(("Street names must contain a max of " +
                    "%d characters.").formatted(MAX_LENGTH));
        }
    }

    private boolean isValid(String name) {
        return name.length() <= MAX_LENGTH && !name.isEmpty();
    }
}
