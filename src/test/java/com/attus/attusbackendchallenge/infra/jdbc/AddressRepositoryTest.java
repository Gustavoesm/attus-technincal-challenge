package com.attus.attusbackendchallenge.infra.jdbc;

import com.attus.attusbackendchallenge.infra.exceptions.PersonNotFoundException;
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
    void shouldThrowWhenTryingToListAddressesForNonExistentPerson() {
        assertThrows(PersonNotFoundException.class, () -> repository.personAddresses(aNonExistentPerson()));
    }

    @Test
    void shouldCorrectlyFindIndexOfAddress() {
        AddressIdentifier index = repository.findIndexOf(aPersonWithAnAddress(), anAddress());
        assertEquals(anAddressId().value(), index.value());
    }

    @Test
    void shouldReturnNullWhenAttemptingToFindNonExistentAddress() {
        AddressIdentifier index = repository.findIndexOf(aPersonWithAnAddress(), aNewAddress());
        assertNull(index);
    }

    @Test
    void shouldReturnNullWhenAttemptingToFindAddressOfNonExistentPerson() {
        AddressIdentifier index = repository.findIndexOf(aNonExistentPerson(), aNewAddress());
        assertNull(index);
    }

    @Test
    void shouldCorrectlyReplaceAnAddress() {
        PersonIdentifier id = aPersonWithAnAddress();
        repository.replaceAddress(anAddressId(), aNewAddress());
        PersonAddresses addresses = repository.personAddresses(id);
        assertEquals(aNewAddress(), addresses.getMainAddress());
    }

    @Test
    void shouldCorrectlyReplaceAnAddressForPersonWithMultipleAddresses() {
        PersonIdentifier id = aPersonWithAnAddress();
        repository.add(id, anotherAddress(), false);
        repository.replaceAddress(anAddressId(), aNewAddress());
        PersonAddresses addresses = repository.personAddresses(id);
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
        PersonIdentifier id = aPersonWithAnAddress();
        repository.add(id, aNewAddress(), false);
        repository.remove(repository.findIndexOf(id, aNewAddress()));
        PersonAddresses addresses = repository.personAddresses(id);
        assertEquals(anAddress(), addresses.getMainAddress());
        assertEquals(1, addresses.listAll().size());
    }

    @Test
    void shouldCorrectlyRemoveAddressWithMultipleAddresses() {
        PersonIdentifier id = aPersonWithAnAddress();
        repository.replaceAddress(anAddressId(), anAddress());
        repository.add(id, anotherAddress(), true);
        repository.add(id, aNewAddress(), false);
        repository.remove(anAddressId());
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
        assertNull(repository.findIndexOf(aPersonWithAnAddress(), anAddress()));
    }

    @Test
    void shouldDoNothingWhenClearingAddressesForNonExistentPerson() {
        AddressIdentifier id = repository.findIndexOf(aPersonWithAnAddress(), anAddress());
        assertEquals(anAddressId().value(), id.value());
        assertDoesNotThrow(() -> repository.clearAddressesFrom(aPersonWithNoAddress()));
    }
}