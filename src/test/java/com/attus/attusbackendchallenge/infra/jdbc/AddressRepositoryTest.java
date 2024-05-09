package com.attus.attusbackendchallenge.infra.jdbc;

import com.attus.attusbackendchallenge.infra.exceptions.PersonNotFoundException;
import com.attus.attusbackendchallenge.model.Address;
import com.attus.attusbackendchallenge.model.AddressIdentifier;
import com.attus.attusbackendchallenge.model.PersonAddresses;
import com.attus.attusbackendchallenge.model.PersonIdentifier;
import com.attus.attusbackendchallenge.model.repositories.AddressRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;

import static com.attus.attusbackendchallenge.fixtures.AddressFixture.*;
import static com.attus.attusbackendchallenge.fixtures.IntegrationTestsFixture.*;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Sql(scripts = {"classpath:dataSetup.sql"})
@ComponentScan
class AddressRepositoryTest {

    @Autowired
    private AddressRepository repository;

    @Test
    void shouldCorrectlyAddAnAddress() {
        PersonIdentifier id = aPersonWithNoAddress();
        repository.add(id, aNewAddress(), true);
        PersonAddresses personAddresses = repository.personAddresses(id);
        assertEquals(aNewAddress(), personAddresses.getMainAddress());
        assertEquals(1, personAddresses.listAll().size());
    }

    @Test
    void shouldCorrectlyAddASecondAddress() {
        PersonIdentifier id = aPersonWithAnAddress();
        repository.add(id, aNewAddress(), false);
        PersonAddresses personAddresses = repository.personAddresses(id);
        assertEquals(anAddress(), personAddresses.getMainAddress());
        assertTrue(personAddresses.listAll().contains(aNewAddress()));
        assertEquals(2, personAddresses.listAll().size());
    }

    @Test
    void shouldThrowWhenTryToAddAnAddressToNonExistentPerson() {
        assertThrows(PersonNotFoundException.class, () -> repository.add(aNonExistentPerson(), aNewAddress(), true));
    }

    @Test
    void shouldCorrectBuildPersonAddresses() {
        PersonAddresses personAddresses = repository.personAddresses(aPersonWithAnAddress());
        assertEquals(1, personAddresses.listAll().size());
        assertEquals(anAddress(), personAddresses.getMainAddress());
    }

    @Test
    void shouldCorrectBuildPersonAddressesWithMultipleAddresses() {
        PersonIdentifier id = aPersonWithAnAddress();
        repository.add(id, anotherAddress(), false);
        repository.add(id, aNewAddress(), false);
        PersonAddresses personAddresses = repository.personAddresses(id);
        assertEquals(3, personAddresses.listAll().size());
        assertEquals(anAddress(), personAddresses.getMainAddress());
        assertTrue(personAddresses.listAll().contains(anotherAddress()));
        assertTrue(personAddresses.listAll().contains(aNewAddress()));
    }

    @Test
    void shouldReturnNullWhenTryingToListAddressesForNonExistentPerson() {
        assertNull(repository.personAddresses(aNonExistentPerson()));
    }

    @Test
    void shouldCorrectlyFindIndexOfAddress() {
        Address address = repository.find(anAddressId());
        assertEquals(anAddress(), address);
    }

    @Test
    void shouldReturnNullWhenAttemptingToFindNonExistentAddress() {
        Address address = repository.find(aNonExistentAddressId());
        assertNull(address);
    }

    @Test
    void shouldCorrectlyReplaceAnAddress() {
        repository.replaceAddress(anAddressId(), aNewAddress());
        PersonAddresses addresses = repository.personAddresses(aPersonWithAnAddress());
        assertEquals(aNewAddress(), addresses.getMainAddress());
    }

    @Test
    void shouldCorrectlyReplaceAnAddressForPersonWithMultipleAddresses() {
        repository.add(aPersonWithAnAddress(), anotherAddress(), false);
        repository.replaceAddress(anAddressId(), aNewAddress());
        PersonAddresses addresses = repository.personAddresses(aPersonWithAnAddress());
        assertEquals(aNewAddress(), addresses.getMainAddress());
        assertEquals(2, addresses.listAll().size());
        assertTrue(addresses.listAll().contains(anotherAddress()));
    }

    @Test
    void shouldReturnFalseWhenUnableToReplaceAddress() {
        assertFalse(repository.replaceAddress(aNonExistentAddressId(), aNewAddress()));
    }

    @Test
    void shouldCorrectlyRemoveAddress() {
        Address addedAddress = repository.add(aPersonWithAnAddress(), aNewAddress(), false);
        repository.remove(addedAddress.identifier());
        PersonAddresses addresses = repository.personAddresses(aPersonWithAnAddress());
        assertEquals(anAddress(), addresses.getMainAddress());
        assertEquals(1, addresses.listAll().size());
    }

    @Test
    void shouldCorrectlyRemoveAddressWithMultipleAddresses() {
        PersonIdentifier id = aPersonWithAnAddress();
        AddressIdentifier addressId = anAddressId();
        repository.replaceAddress(addressId, anAddress());
        repository.add(id, anotherAddress(), true);
        repository.add(id, aNewAddress(), false);
        repository.remove(addressId);
        PersonAddresses addresses = repository.personAddresses(id);
        assertEquals(anotherAddress(), addresses.getMainAddress());
        assertTrue(addresses.listAll().contains(aNewAddress()));
        assertEquals(2, addresses.listAll().size());
    }

    @Test
    void shouldReturnFalseWhenUnableToRemoveAddress() {
        assertFalse(repository.remove(aNonExistentAddressId()));
    }

    @Test
    void shouldCorrectlyClearAllAddressesForPerson() {
        repository.clearAddressesFrom(aPersonWithAnAddress());
        assertNull(repository.find(anAddressId()));
    }

    @Test
    void shouldDoNothingWhenClearingAddressesForNonExistentPerson() {
        Address address = repository.find(anAddressId());
        assertEquals(anAddressId().value(), address.identifier().value());
        assertDoesNotThrow(() -> repository.clearAddressesFrom(aPersonWithNoAddress()));
    }
}