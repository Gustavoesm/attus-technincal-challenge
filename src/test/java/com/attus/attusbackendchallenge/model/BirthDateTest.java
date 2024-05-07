package com.attus.attusbackendchallenge.model;

import com.attus.attusbackendchallenge.model.exceptions.InvalidDateException;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static com.attus.attusbackendchallenge.fixtures.DateFixture.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BirthDateTest {

    @Test
    void shouldCorrectlyBuildObjectForValidDates() {
        assertDoesNotThrow(() -> new BirthDate(dateOfDecember31th2000()));
        assertDoesNotThrow(() -> new BirthDate(dateOfJanuary1st1900()));
        assertDoesNotThrow(() -> new BirthDate(dateOfMay27th1997()));
        assertDoesNotThrow(() -> new BirthDate(dateOfOctober12th1950()));
        assertDoesNotThrow(() -> new BirthDate(dateOfToday()));
    }

    @Test
    void shouldThrowExceptionForDatesBefore1900() {
        assertThrows(InvalidDateException.class, () -> new BirthDate(dateOfDecember31th1899()));
        assertThrows(InvalidDateException.class, () -> new BirthDate(dateOfDecember31th1899()));
    }

    @Test
    void verifyEqualsContract() {
        EqualsVerifier.simple().forClass(BirthDate.class).withNonnullFields("value").verify();
    }
}
