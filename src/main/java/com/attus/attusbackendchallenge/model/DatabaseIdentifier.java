package com.attus.attusbackendchallenge.model;

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
}
