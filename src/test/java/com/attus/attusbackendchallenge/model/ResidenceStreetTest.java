package com.attus.attusbackendchallenge.model;

import com.attus.attusbackendchallenge.model.exceptions.InvalidResidenceStreetException;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResidenceStreetTest {
    @Test
    void shouldNotThrowWhenStreetNameDoesNotExceedsMaxLength() {
        assertDoesNotThrow(() -> new ResidenceStreet("Travessa Maravilha Tristeza"));
        assertDoesNotThrow(() -> new ResidenceStreet("Rua das Pedras"));
        assertDoesNotThrow(() -> new ResidenceStreet("Rua Gonçalo de Carvalho"));
        assertDoesNotThrow(() -> new ResidenceStreet("Rua do Lazer"));
        assertDoesNotThrow(() -> new ResidenceStreet("Rua Torta"));
        assertDoesNotThrow(() -> new ResidenceStreet("Avenida Brasil"));
    }

    @Test
    void shouldThrowWhenStreetNameExceedsMaxLength() {
        assertThrows(InvalidResidenceStreetException.class, () -> new ResidenceStreet("aaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaa"));
    }

    @Test
    void shouldThrowIfNameIsEmptyOrBlank() {
        assertThrows(InvalidResidenceStreetException.class, () -> new ResidenceStreet(""));
        assertThrows(InvalidResidenceStreetException.class, () -> new ResidenceStreet("    "));
    }

    @Test
    void verifyEqualsContract() {
        EqualsVerifier.simple().forClass(ResidenceStreet.class).withNonnullFields("name").verify();
    }
}