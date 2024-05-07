package com.attus.attusbackendchallenge.model;

import com.attus.attusbackendchallenge.model.exceptions.InvalidResidenceIdentifierException;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResidenceIdentifierTest {
    @Test
    void shouldNotThrowIfValidIdentifier() {
        assertDoesNotThrow(() -> new ResidenceIdentifier("18"));
        assertDoesNotThrow(() -> new ResidenceIdentifier("128A"));
        assertDoesNotThrow(() -> new ResidenceIdentifier("992 (2)"));
        assertDoesNotThrow(() -> new ResidenceIdentifier("Sem número"));
        assertDoesNotThrow(() -> new ResidenceIdentifier("s/n"));
    }

    @Test
    void shouldThrowIfEmptyString() {
        assertThrows(InvalidResidenceIdentifierException.class, () -> new ResidenceIdentifier(""));
        assertThrows(InvalidResidenceIdentifierException.class, () -> new ResidenceIdentifier("    "));
    }

    @Test
    void shouldThrowIfExceedMaxLength() {
        assertThrows(InvalidResidenceIdentifierException.class,
                () -> new ResidenceIdentifier("12345678901234567"));

    }

    @Test
    void verifyEqualsContract() {
        EqualsVerifier.simple().forClass(ResidenceIdentifier.class).withNonnullFields("value").verify();
    }
}