package com.attus.attusbackendchallenge.model;

import com.attus.attusbackendchallenge.model.exceptions.InvalidResidenceStreetException;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResidenceStreet that)) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
