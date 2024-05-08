package com.attus.attusbackendchallenge.service;

import com.attus.attusbackendchallenge.infra.exceptions.PersonNotFoundException;
import com.attus.attusbackendchallenge.model.Address;
import com.attus.attusbackendchallenge.model.AddressIdentifier;
import com.attus.attusbackendchallenge.model.PersonAddresses;
import com.attus.attusbackendchallenge.model.PersonIdentifier;
import com.attus.attusbackendchallenge.model.repositories.AddressRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static com.attus.attusbackendchallenge.fixtures.AddressFixture.anAddress;
import static com.attus.attusbackendchallenge.fixtures.AddressFixture.anotherAddress;
import static com.attus.attusbackendchallenge.fixtures.IntegrationTestsFixture.*;
import static com.attus.attusbackendchallenge.fixtures.PersonAddressFixture.aPersonAddresses;
import static org.mockito.Mockito.*;

@SpringBootTest
class AddressServiceTest {

    @Autowired
    private AddressService service;

    @MockBean
    private AddressRepository repository;

    @Test
    void shouldDelegateAdditionToRepositoryOnFirstAddress() {
        PersonIdentifier personId = aPersonWithNoAddress();
        Address address = anAddress();
        when(repository.personAddresses(personId)).thenThrow(PersonNotFoundException.class);
        service.addAddressTo(personId, address);
        verify(repository).personAddresses(personId);
        verify(repository).add(personId, address, true);
    }

    @Test
    void shouldDelegateAdditionToRepositoryOnOtherAddress() {
        PersonIdentifier personId = aPersonWithAnAddress();
        Address address = anAddress();
        when(repository.personAddresses(personId)).thenReturn(aPersonAddresses());
        service.addAddressTo(personId, address);
        verify(repository).personAddresses(personId);
        verify(repository).add(personId, address, false);
    }

    @Test
    void shouldDelegateListingToRepository() {
        PersonIdentifier personId = aPersonWithAnAddress();
        service.listAddressesFor(personId);
        verify(repository).personAddresses(personId);
    }

    @Test
    void shouldDelegateReplaceToRepository() {
        PersonIdentifier personId = aPersonWithAnAddress();
        AddressIdentifier addressId = anAddressId();
        Address oldAddress = anAddress();
        Address newAddress = mock(Address.class);
        when(repository.findIndexOf(personId, oldAddress)).thenReturn(addressId);
        service.replaceAddress(personId, oldAddress, newAddress);
        verify(newAddress).setIdentifier(addressId);
        verify(repository).replaceAddress(addressId, newAddress);
    }

    @Test
    void shouldDelegateRemoveToRepository() {
        PersonIdentifier personId = aPersonWithAnAddress();
        Address address = anAddress();
        AddressIdentifier addressId = anAddressId();
        when(repository.findIndexOf(personId, address)).thenReturn(addressId);
        service.removeAddress(personId, address);
        verify(repository).remove(addressId);
    }

    @Test
    void changeMainAddressAndDelegateReplaceToRepository() {
        PersonIdentifier personId = aPersonWithAnAddress();
        Address address = anotherAddress();
        AddressIdentifier addressId = anAddressId();
        PersonAddresses mockedAddresses = mock(PersonAddresses.class);
        Address mock = mock(Address.class);
        when(repository.personAddresses(personId)).thenReturn(mockedAddresses);
        when(mockedAddresses.listAll()).thenReturn(List.of(mock, mock, mock));
        when(mock.identifier()).thenReturn(addressId);
        service.changeMainAddress(personId, address);
        verify(mockedAddresses).changeMainAddress(address);
        verify(repository, times(3)).replaceAddress(addressId, mock);
    }

    @Test
    void shouldDelegateClearToRepository() {
        PersonIdentifier personId = aPersonWithAnAddress();
        service.clearAddressesFromPerson(personId);
        verify(repository).clearAddressesFrom(personId);
    }
}