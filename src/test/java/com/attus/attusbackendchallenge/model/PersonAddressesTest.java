package com.attus.attusbackendchallenge.model;

import com.attus.attusbackendchallenge.model.exceptions.AddressNotFoundException;
import com.attus.attusbackendchallenge.model.exceptions.MainAddressRemovalException;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static com.attus.attusbackendchallenge.fixtures.AddressFixture.*;
import static com.attus.attusbackendchallenge.fixtures.PersonAddressFixture.aPersonAddresses;
import static org.junit.jupiter.api.Assertions.*;

class PersonAddressesTest {

    @Test
    void shouldCorrectlyRetrieveAllAddresses() {
        assertEquals(1, aPersonAddresses().listAll().size());
        assertTrue(aPersonAddresses().listAll().contains(anAddress()));
    }

    @Test
    void shouldCorrectlyRetrieveMainAddress() {
        assertEquals(anAddress(), aPersonAddresses().getMainAddress());
    }

    @Test
    void shouldCorrectlyAddAnAddressesToList() {
        PersonAddresses testSubject = aPersonAddresses();
        testSubject.addAddress(aNewAddress());
        assertEquals(2, testSubject.listAll().size());
        assertTrue(testSubject.listAll().contains(aNewAddress()));
        assertEquals(anAddress(), testSubject.getMainAddress());
    }

    @Test
    void shouldCorrectlyChangeMainAddress() {
        PersonAddresses testSubject = aPersonAddresses();
        testSubject.addAddress(aNewAddress());
        testSubject.changeMainAddress(aNewAddress());
        assertEquals(aNewAddress(), testSubject.getMainAddress());
    }

    @Test
    void shouldThrowWhenUnableToFindNewMainAddress() {
        assertFalse(aPersonAddresses().listAll().contains(aNewAddress()));
        assertThrows(AddressNotFoundException.class,
                () -> aPersonAddresses().changeMainAddress(aNewAddress()));
    }

    @Test
    void shouldCorrectlyRemoveNonMainAddress() {
        PersonAddresses testSubject = aPersonAddresses();
        testSubject.addAddress(anotherAddress());
        testSubject.addAddress(aNewAddress());
        assertEquals(3, testSubject.listAll().size());
        assertTrue(testSubject.listAll().contains(anotherAddress()));
        testSubject.removeAddress(anotherAddress());
        assertEquals(2, testSubject.listAll().size());
        assertFalse(testSubject.listAll().contains(anotherAddress()));
    }

    @Test
    void shouldCorrectlyRecoverMainAddressAfterLowerIndexAddressRemoval() {
        PersonAddresses testSubject = aPersonAddresses();
        testSubject.addAddress(anotherAddress());
        testSubject.addAddress(aNewAddress());
        testSubject.changeMainAddress(aNewAddress());
        testSubject.removeAddress(anAddress());
        assertEquals(aNewAddress(), testSubject.getMainAddress());
    }

    @Test
    void shouldThrowWhenAttemptingToRemoveLastAddress() {
        assertThrows(MainAddressRemovalException.class, () -> aPersonAddresses().removeAddress(anAddress()));
    }

    @Test
    void shouldThrowWhenAttemptingToRemoveMainAddress() {
        PersonAddresses testSubject = aPersonAddresses();
        testSubject.addAddress(anotherAddress());
        assertThrows(MainAddressRemovalException.class, () -> testSubject.removeAddress(anAddress()));
    }

    @Test
    void shouldCorrectlyReplaceAddressData() {
        PersonAddresses testSubject = aPersonAddresses();
        testSubject.replaceAddress(anAddress(), aNewAddress());
        assertEquals(1, testSubject.listAll().size());
        assertEquals(aNewAddress(), testSubject.getMainAddress());
    }

    @Test
    void shouldCorrectlyReplaceMainAddressData() {
        PersonAddresses testSubject = aPersonAddresses();
        testSubject.addAddress(anotherAddress());
        testSubject.addAddress(aNewAddress());
        testSubject.changeMainAddress(anotherAddress());
        testSubject.replaceAddress(anotherAddress(), aSecondNewAddress());
        assertEquals(aSecondNewAddress(), testSubject.getMainAddress());
        assertEquals(3, testSubject.listAll().size());
    }

    @Test
    void verifyEqualsContract() {
        EqualsVerifier.simple().forClass(PersonAddresses.class).verify();
    }
}