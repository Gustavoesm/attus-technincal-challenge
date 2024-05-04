package com.attus.attusbackendchallenge.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class BrazilianStatesTest {

    @Test
    void shouldReturnStatesShortNames() {
        assertAll("All states short names should be 2 chars length", () -> {
            Arrays.stream(BrazilianStates.values()).forEach(
                    it -> assertEquals(2, it.shortName().length())
            );
        });
    }

    @Test
    void shouldReturnStatesLongNames() {
        assertAll("All states long names should be non-null and not empty", () -> {
            Arrays.stream(BrazilianStates.values()).forEach(
                    it -> {
                        assertNotNull(it.longName());
                        assertFalse(it.longName().isEmpty());
                    }
            );
        });
    }
}
