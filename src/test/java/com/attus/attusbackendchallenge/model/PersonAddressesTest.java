package com.attus.attusbackendchallenge.model;

import com.attus.attusbackendchallenge.model.exceptions.AddressNotFoundException;
import com.attus.attusbackendchallenge.model.exceptions.MainAddressNotFoundException;
import com.attus.attusbackendchallenge.model.exceptions.MainAddressRemovalException;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
    void shouldThrowWhenUnableToRetrieveMainAddress() {
        PersonAddresses testSubject = new PersonAddresses(List.of(anAddress()), DatabaseIdentifier.of(0));
        assertThrows(MainAddressNotFoundException.class, testSubject::getMainAddress);
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
        Address main = anAddress();
        Address another = anotherAddress();
        another.setIdentifier(DatabaseIdentifier.of(1));
        AddressIdentifier newMain = DatabaseIdentifier.of(2);
        Address another2 = aNewAddress();
        another2.setIdentifier(newMain);

        PersonAddresses testSubject = new PersonAddresses(List.of(main, another, another2), main.identifier());
        testSubject.changeMainAddress(newMain);
        assertEquals(aNewAddress(), testSubject.getMainAddress());
    }

    @Test
    void shouldThrowWhenUnableToFindNewMainAddress() {
        PersonAddresses testSubject = aPersonAddresses();
        assertFalse(testSubject.listAll().contains(aNewAddress()));
        assertThrows(AddressNotFoundException.class,
                () -> testSubject.changeMainAddress(DatabaseIdentifier.of(-1)));
    }

    @Test
    void shouldCorrectlyRemoveNonMainAddress() {
        Address main = anAddress();
        Address anotherAddress = anotherAddress();
        anotherAddress.setIdentifier(DatabaseIdentifier.of(1));
        Address newAddress = aNewAddress();
        anotherAddress.setIdentifier(DatabaseIdentifier.of(2));
        PersonAddresses testSubject = new PersonAddresses(new ArrayList<>(List.of(main, anotherAddress, newAddress)), main.identifier());
        testSubject.removeAddress(anotherAddress.identifier());
        assertEquals(2, testSubject.listAll().size());
        assertFalse(testSubject.listAll().contains(anotherAddress));
    }

    @Test
    void shouldThrowWhenAttemptingToRemoveLastAddress() {
        assertThrows(MainAddressRemovalException.class, () -> aPersonAddresses().removeAddress(anAddress().identifier()));
    }

    @Test
    void shouldThrowWhenAttemptingToRemoveMainAddress() {
        PersonAddresses testSubject = aPersonAddresses();
        Address anotherAddress = anotherAddress();
        anotherAddress.setIdentifier(DatabaseIdentifier.of(1));
        testSubject.addAddress(anotherAddress);
        assertThrows(MainAddressRemovalException.class, () -> testSubject.removeAddress(anAddress().identifier()));
    }

    @Test
    void shouldCorrectlyReplaceAddressData() {
        PersonAddresses testSubject = aPersonAddresses();
        Address aNewAddress = aNewAddress();
        aNewAddress.setIdentifier(testSubject.getMainAddress().identifier());
        testSubject.replaceAddress(testSubject.getMainAddress().identifier(), aNewAddress);
        assertEquals(1, testSubject.listAll().size());
        assertEquals(aNewAddress, testSubject.getMainAddress());
    }

    @Test
    void shouldCorrectlyReplaceMainAddressData() {
        PersonAddresses testSubject = aPersonAddresses();
        Address anotherAddress = anotherAddress();
        AddressIdentifier sameAddress = DatabaseIdentifier.of(1);
        anotherAddress.setIdentifier(sameAddress);
        Address newAddress = aNewAddress();
        newAddress.setIdentifier(DatabaseIdentifier.of(2));
        Address aSecondNewAddress = aSecondNewAddress();
        aSecondNewAddress.setIdentifier(sameAddress);

        testSubject.addAddress(anotherAddress);
        testSubject.addAddress(newAddress);
        testSubject.changeMainAddress(anotherAddress.identifier());
        testSubject.replaceAddress(anotherAddress.identifier(), aSecondNewAddress);
        assertEquals(aSecondNewAddress, testSubject.getMainAddress());
        assertEquals(3, testSubject.listAll().size());
    }

    @Test
    void verifyEqualsContract() {
        EqualsVerifier.simple().forClass(PersonAddresses.class).verify();
    }
}