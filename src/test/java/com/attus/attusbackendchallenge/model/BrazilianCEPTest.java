package com.attus.attusbackendchallenge.model;

import com.attus.attusbackendchallenge.model.exceptions.InvalidCEPException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BrazilianCEPTest {
    @Test
    void shouldNotThrowForValidPostalCodes() {
        assertDoesNotThrow(() -> new BrazilianCEP("13000-000"));
        assertDoesNotThrow(() -> new BrazilianCEP("12345-678"));
        assertDoesNotThrow(() -> new BrazilianCEP("99999-999"));
    }

    @Test
    void shouldThrowIfMissingOrMissplacedHyphen() {
        assertThrows(InvalidCEPException.class, () -> new BrazilianCEP("13000000"));
        assertThrows(InvalidCEPException.class, () -> new BrazilianCEP("13-000000"));
        assertThrows(InvalidCEPException.class, () -> new BrazilianCEP("1300000-0"));
    }

    @Test
    void shouldThrowIfIncorrectNumberOfDigits() {
        assertThrows(InvalidCEPException.class, () -> new BrazilianCEP("12345-6789"));
        assertThrows(InvalidCEPException.class, () -> new BrazilianCEP("12345-67"));
        assertThrows(InvalidCEPException.class, () -> new BrazilianCEP("12345"));
    }
}