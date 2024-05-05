package com.attus.attusbackendchallenge.model;

import com.attus.attusbackendchallenge.model.exceptions.InvalidCityException;

import java.util.Objects;

public record City(String name) {
    private static final int MAX_LENGTH = 128;

    public City {
        name = name.trim();
        if (!isValid(name)) {
            throw new InvalidCityException("City names must be between 2 and 128 characters long " +
                    "and consist only of letters and the following punctuations ('-,.)");
        }
    }

    private boolean isValid(String name) {
        final String containsOnlyLettersAndValidPunctuation = "(?i)[a-z]([-',.]? ?[a-zãõáéíóúâêîôûàèìòù])+";
        return name.length() <= MAX_LENGTH
                && name.matches(containsOnlyLettersAndValidPunctuation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City city)) return false;
        return Objects.equals(name, city.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
