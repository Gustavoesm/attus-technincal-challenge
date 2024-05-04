package com.attus.attusbackendchallenge.model;

import com.attus.attusbackendchallenge.model.exceptions.InvalidCEPException;

public record BrazilianCEP(String value) implements PostalCodeFormat {
    public BrazilianCEP {
        if (!isValid(value)) {
            throw new InvalidCEPException("Invalid postal code format, please use the following " +
                    "format without parenthesis: ( XXXXX-XXX ).");
        }
    }

    private boolean isValid(String value) {
        String postalCodeRegex = "(^\\d{5})-(\\d{3}$)";
        return value.matches(postalCodeRegex);
    }
}
