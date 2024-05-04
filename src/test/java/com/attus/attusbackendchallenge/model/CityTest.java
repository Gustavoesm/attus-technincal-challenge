package com.attus.attusbackendchallenge.model;

import com.attus.attusbackendchallenge.model.exceptions.InvalidCityException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CityTest {
    @Test
    void shouldAcceptValidCityNames() {
        assertDoesNotThrow(() -> new City("Teste"));
        assertDoesNotThrow(() -> new City("Limeira"));
        assertDoesNotThrow(() -> new City("São Paulo"));
        assertDoesNotThrow(() -> new City("Sertãozinho"));
        assertDoesNotThrow(() -> new City("Rolândia"));
        assertDoesNotThrow(() -> new City("Dois Vizinhos"));
        assertDoesNotThrow(() -> new City("Xique-Xique"));
        assertDoesNotThrow(() -> new City("Iracemápolis"));
        assertDoesNotThrow(() -> new City("St. Paul"));
        assertDoesNotThrow(() -> new City("Valão, sul"));
        assertDoesNotThrow(() -> new City("T'est en-art ãõáéíóúâêîôûàèìòù"));
    }

    @Test
    void shouldThrowExceptionForInvalidCharactersInCityNames() {
        assertThrows(InvalidCityException.class, () -> new City("T?este"));
        assertThrows(InvalidCityException.class, () -> new City("T!este"));
        assertThrows(InvalidCityException.class, () -> new City("T#este"));
        assertThrows(InvalidCityException.class, () -> new City("T@este"));
        assertThrows(InvalidCityException.class, () -> new City("T$este"));
        assertThrows(InvalidCityException.class, () -> new City("T%este"));
        assertThrows(InvalidCityException.class, () -> new City("T&este"));
        assertThrows(InvalidCityException.class, () -> new City("T*este"));
        assertThrows(InvalidCityException.class, () -> new City("T(este"));
        assertThrows(InvalidCityException.class, () -> new City("T)este"));
        assertThrows(InvalidCityException.class, () -> new City("T_este"));
        assertThrows(InvalidCityException.class, () -> new City("T=este"));
        assertThrows(InvalidCityException.class, () -> new City("T+este"));
        assertThrows(InvalidCityException.class, () -> new City("T\neste"));
        assertThrows(InvalidCityException.class, () -> new City("T\teste"));
        assertThrows(InvalidCityException.class, () -> new City("T\\este"));
        assertThrows(InvalidCityException.class, () -> new City("T|este"));
        assertThrows(InvalidCityException.class, () -> new City("T:este"));
        assertThrows(InvalidCityException.class, () -> new City("T;este"));
        assertThrows(InvalidCityException.class, () -> new City("T/este"));
        assertThrows(InvalidCityException.class, () -> new City("T\"este"));
        assertThrows(InvalidCityException.class, () -> new City("T[este"));
        assertThrows(InvalidCityException.class, () -> new City("T]este"));
        assertThrows(InvalidCityException.class, () -> new City("T{este"));
        assertThrows(InvalidCityException.class, () -> new City("T}este"));
        assertThrows(InvalidCityException.class, () -> new City("T~este"));
        assertThrows(InvalidCityException.class, () -> new City("T^este"));
        assertThrows(InvalidCityException.class, () -> new City("T´este"));
        assertThrows(InvalidCityException.class, () -> new City("T`este"));
    }

    @Test
    void shouldThrowExceptionIfNameIsTooLongOrTooShort() {
        assertThrows(InvalidCityException.class, () -> new City("a"));
        assertThrows(InvalidCityException.class, () -> new City("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
    }

    @Test
    void shouldThrowExceptionIfNameEndsInValidPunctuation() {
        assertThrows(InvalidCityException.class, () -> new City("Teste'"));
        assertThrows(InvalidCityException.class, () -> new City("Teste."));
        assertThrows(InvalidCityException.class, () -> new City("Teste-"));
        assertThrows(InvalidCityException.class, () -> new City("Teste,"));
    }

    @Test
    void shouldThrowExceptionIfNameHasMultiplePunctuationsInARow() {
        assertThrows(InvalidCityException.class, () -> new City("Tes--te"));
        assertThrows(InvalidCityException.class, () -> new City("Tes..te"));
        assertThrows(InvalidCityException.class, () -> new City("Tes''te"));
        assertThrows(InvalidCityException.class, () -> new City("Tes,,te"));
    }
}