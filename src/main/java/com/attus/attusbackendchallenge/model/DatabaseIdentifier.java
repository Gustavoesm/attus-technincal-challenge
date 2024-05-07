package com.attus.attusbackendchallenge.model;

public class DatabaseIdentifier implements PersonIdentifier, AddressIdentifier {
    private final Long value;

    public DatabaseIdentifier(long value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value.toString();
    }
}
