package com.attus.attusbackendchallenge.model;

import com.attus.attusbackendchallenge.model.exceptions.InvalidResidenceIdentifierException;

import java.util.Objects;

public record ResidenceIdentifier(String value) {
    private static final int MAX_LENGTH = 16;

    public ResidenceIdentifier {
        value = value.trim();
        if (!isValid(value)) {
            throw new InvalidResidenceIdentifierException(("Residence identifiers must not be empty and contain" +
                    " a max of %d characters.").formatted(MAX_LENGTH));
        }
    }

    private boolean isValid(String value) {
        return value.length() <= MAX_LENGTH && !value.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResidenceIdentifier that)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
