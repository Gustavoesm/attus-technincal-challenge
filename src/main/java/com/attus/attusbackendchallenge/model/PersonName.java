package com.attus.attusbackendchallenge.model;

import com.attus.attusbackendchallenge.model.exceptions.InvalidPersonNameException;

public record PersonName(String value) {
    private static final int MAX_LENGTH = 64;

    public PersonName {
        value = value.trim();
        if (!isValid(value)) {
            throw new InvalidPersonNameException("Person names can only contain letters, spaces or punctuation \"'-.\".");
        }
    }

    private boolean isValid(String name) {
        String CONTAINS_ONLY_LETTERS_OR_VALID_PUNCTUATION = "(?i)[a-z]([-'.]? ?[a-zãõáéíóúâêîôûàèìòù])+";
        return name.length() <= MAX_LENGTH
                && name.matches(CONTAINS_ONLY_LETTERS_OR_VALID_PUNCTUATION);
    }
}
