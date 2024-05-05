package com.attus.attusbackendchallenge.model;

import org.junit.jupiter.api.Test;

import static com.attus.attusbackendchallenge.fixtures.BirthDateFixture.aBirthDate;
import static com.attus.attusbackendchallenge.fixtures.BirthDateFixture.anotherBirthDate;
import static com.attus.attusbackendchallenge.fixtures.PersonAddressFixture.aPersonAddresses;
import static com.attus.attusbackendchallenge.fixtures.PersonFixture.aPerson;
import static com.attus.attusbackendchallenge.fixtures.PersonFixture.anotherPerson;
import static com.attus.attusbackendchallenge.fixtures.PersonNameFixture.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonTest {

    @Test
    void shouldCorrectlyRecoverIdentifier() {
        // TODO: implement when database models are defined
    }

    @Test
    void shouldCorrectlyRecoverFirstName() {
        assertEquals(aFirstName().value(), aPerson().firstName().value());
    }

    @Test
    void shouldCorrectlyRecoverLastName() {
        assertEquals(aLastName().value(), aPerson().lastName().value());
    }

    @Test
    void shouldCorrectlyRecoverBirthDate() {
        assertEquals(aBirthDate().value(), aPerson().birthDate().value());
    }

    @Test
    void shouldCorrectlyRecoverAddresses() {
        assertEquals(aPersonAddresses().getMainAddress().postalCode().value(),
                aPerson().addresses().getMainAddress().postalCode().value());
    }

    @Test
    void setIdentifier() {
        // TODO: implement when database models are defined
    }

    @Test
    void shouldCorrectlyUpdateFirstName() {
        assertEquals(aFirstName().value(), aPerson().firstName().value());
        Person updatedPerson = aPerson();
        updatedPerson.setFirstName(anotherFirstName());
        assertEquals(anotherFirstName().value(), anotherPerson().firstName().value());
    }

    @Test
    void shouldCorrectlyUpdateLastName() {
        assertEquals(aLastName().value(), aPerson().lastName().value());
        Person updatedPerson = aPerson();
        updatedPerson.setLastName(anotherLastName());
        assertEquals(anotherLastName().value(), anotherPerson().lastName().value());
    }

    @Test
    void shouldCorrectlyUpdateBirthDate() {
        assertEquals(aBirthDate().value(), aPerson().birthDate().value());
        Person updatedPerson = aPerson();
        updatedPerson.setBirthDate(anotherBirthDate());
        assertEquals(anotherBirthDate().value(), anotherPerson().birthDate().value());
    }
}