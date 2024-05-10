package com.attus.attusbackendchallenge.model;

import java.util.Objects;

public class DatabaseIdentifier implements PersonIdentifier, AddressIdentifier {
    private final Long value;

    private DatabaseIdentifier(long value) {
        this.value = value;
    }

    public static DatabaseIdentifier of(long value) {
        return new DatabaseIdentifier(value);
    }

    @Override
    public String value() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatabaseIdentifier that)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
