package com.attus.attusbackendchallenge.model;

import com.attus.attusbackendchallenge.model.exceptions.InvalidPersonNameException;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PersonNameTest {

    @Test
    void shouldNotThrowForValidNames() {
        assertDoesNotThrow(() -> new PersonName("Test"));
        assertDoesNotThrow(() -> new PersonName("Vagner"));
        assertDoesNotThrow(() -> new PersonName("Caio Lucas"));
        assertDoesNotThrow(() -> new PersonName("Joana D'arc"));
        assertDoesNotThrow(() -> new PersonName("Deacon St. John"));
        assertDoesNotThrow(() -> new PersonName("Pierre-Emerick Aubameyang"));
    }

    @Test
    void shouldThrowWhenNameHasInvalidPunctuation() {
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("T?este"));
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("T!este"));
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("T#este"));
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("T@este"));
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("T$este"));
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("T%este"));
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("T&este"));
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("T*este"));
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("T(este"));
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("T)este"));
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("T_este"));
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("T=este"));
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("T+este"));
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("T\neste"));
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("T\teste"));
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("T\\este"));
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("T|este"));
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("T:este"));
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("T;este"));
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("T/este"));
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("T\"este"));
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("T[este"));
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("T]este"));
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("T{este"));
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("T}este"));
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("T~este"));
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("T^este"));
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("T´este"));
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("T`este"));
    }

    @Test
    void shouldThrowExceptionIfNameIsTooLongOrTooShort() {
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("a"));
        assertThrows(InvalidPersonNameException.class, () -> new PersonName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaa"));
    }

    @Test
    void verifyEqualsContract() {
        EqualsVerifier.simple().forClass(PersonName.class).withNonnullFields("value").verify();
    }
}