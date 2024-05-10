package com.attus.attusbackendchallenge.fixtures;

import com.attus.attusbackendchallenge.model.ResidenceIdentifier;

public class ResidenceIdentifierFixture {
    public static ResidenceIdentifier aNumber() {
        return new ResidenceIdentifier("1");
    }

    public static ResidenceIdentifier anotherNumber() {
        return new ResidenceIdentifier("110");
    }

    public static ResidenceIdentifier aNewNumber() {
        return new ResidenceIdentifier("2901");
    }

    public static ResidenceIdentifier aSecondNewResidenceIdentifier() {
        return new ResidenceIdentifier("10");
    }
}
