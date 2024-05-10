package com.attus.attusbackendchallenge.service;

import com.attus.attusbackendchallenge.model.*;
import com.attus.attusbackendchallenge.model.exceptions.AddressNotFoundException;
import com.attus.attusbackendchallenge.model.repositories.AddressRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static com.attus.attusbackendchallenge.fixtures.AddressFixture.*;
import static com.attus.attusbackendchallenge.fixtures.IntegrationTestsFixture.*;
import static com.attus.attusbackendchallenge.fixtures.PersonAddressFixture.aPersonAddresses;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        when(repository.personAddresses(personId)).thenReturn(null);
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
        Address originalAddress = anAddress();
        Address newAddress = aNewAddress();
        when(repository.find(originalAddress.identifier())).thenReturn(originalAddress);
        service.replaceAddress(originalAddress.identifier(), newAddress);
        verify(repository).replaceAddress(originalAddress.identifier(), newAddress);
    }

    @Test
    void shouldDelegateRemoveToRepository() {
        Address address = anAddress();
        Address mock = mock(Address.class);
        AddressIdentifier addressId = anAddressId();
        AddressIdentifier mockId = DatabaseIdentifier.of(999);

        PersonAddresses personAddresses = new PersonAddresses(new ArrayList<>(List.of(address, mock, mock, mock)), mockId);
        PersonIdentifier mockedPersonId = mock(PersonIdentifier.class);

        when(mock.identifier()).thenReturn(mockId);
        when(repository.find(addressId)).thenReturn(address);
        when(repository.findOwner(addressId)).thenReturn(mockedPersonId);
        when(repository.personAddresses(mockedPersonId)).thenReturn(personAddresses);
        when(repository.remove(addressId)).thenReturn(true);
        service.removeAddress(addressId);
        verify(repository).remove(addressId);
    }

    @Test
    void shouldThrowWhenRepositoryUnableToRemove() {
        Address mockedAddress = mock(Address.class);
        AddressIdentifier mockedId = mock(AddressIdentifier.class);
        PersonIdentifier mock = mock(PersonIdentifier.class);

        when(repository.find(mockedId)).thenReturn(mockedAddress);
        when(repository.findOwner(mockedId)).thenReturn(mock);
        when(repository.personAddresses(mock)).thenReturn(aPersonAddresses());
        when(repository.remove(mockedId)).thenReturn(false);
        assertThrows(AddressNotFoundException.class, () -> service.removeAddress(mockedId));
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
        service.changeMainAddress(personId, address.identifier());
        verify(mockedAddresses).changeMainAddress(address.identifier());
        verify(repository, times(3)).replaceAddress(addressId, mock);
    }

    @Test
    void shouldDelegateClearToRepository() {
        PersonIdentifier personId = aPersonWithAnAddress();
        service.clearAddressesFromPerson(personId);
        verify(repository).clearAddressesFrom(personId);
    }
}