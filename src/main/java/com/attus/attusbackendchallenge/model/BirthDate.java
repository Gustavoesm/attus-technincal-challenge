package com.attus.attusbackendchallenge.model;

import com.attus.attusbackendchallenge.model.exceptions.InvalidDateException;

import java.util.Date;
import java.util.Objects;

public record BirthDate(Date value) {
    public BirthDate {
        if (!isValid(value)) {
            throw new InvalidDateException("Please select a valid birth date.");
        }
    }

    private boolean isValid(Date value) {
        Date JANUARY_1ST_1900 = new Date(-2208977612000L);
        return !value.before(JANUARY_1ST_1900) && !value.after(today());
    }

    private Date today() {
        return new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BirthDate birthDate)) return false;
        return Objects.equals(value, birthDate.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
