package com.attus.attusbackendchallenge.service;

import com.attus.attusbackendchallenge.infra.exceptions.InvalidPersonException;
import com.attus.attusbackendchallenge.model.DatabaseIdentifier;
import com.attus.attusbackendchallenge.model.Person;
import com.attus.attusbackendchallenge.model.PersonIdentifier;
import com.attus.attusbackendchallenge.model.repositories.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static com.attus.attusbackendchallenge.fixtures.IntegrationTestsFixture.aPersonWithAnAddress;
import static com.attus.attusbackendchallenge.fixtures.PersonFixture.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PersonServiceTest {

    @Autowired
    private PersonService service;

    @MockBean
    private AddressService addressService;

    @MockBean
    private PersonRepository repository;

    @Test
    void shouldDelegateAddToRepository() {
        Person person = aPerson();
        when(repository.add(any())).thenReturn(aPersonWithAnAddress());
        Person returnedPerson = service.addPerson(person);
        verify(repository).add(person);
        assertEquals(aPersonWithAnAddress().value(), returnedPerson.identifier().value());
    }

    @Test
    void shouldThrowWhenAttemptingToAddWithoutRequiredFields() {
        assertThrows(InvalidPersonException.class, () -> service.addPerson(aPersonWithoutFirstName()));
        assertThrows(InvalidPersonException.class, () -> service.addPerson(aPersonWithoutLastName()));
        assertThrows(InvalidPersonException.class, () -> service.addPerson(aPersonWithoutBirthDate()));
    }

    @Test
    void shouldDelegateFindToRepository() {
        PersonIdentifier id = aPersonWithAnAddress();
        Person mockedPerson = mock(Person.class);
        when(repository.find(id)).thenReturn(mockedPerson);
        service.getPerson(id);
        verify(repository).find(id);
        verify(addressService).listAddressesFor(id);
    }

    @Test
    void shouldDelegateUpdateToRepository() {
        PersonIdentifier id = aPersonWithAnAddress();
        Person person = aPerson();
        Person mockedPerson = mock(Person.class);
        when(repository.find(id)).thenReturn(mockedPerson);
        service.updatePerson(id, person);
        verify(repository).find(id);
        verify(repository).replace(id, mockedPerson);
    }

    @Test
    void shouldDelegateRemoveToRepository() {
        PersonIdentifier id = aPersonWithAnAddress();
        Person mockedPerson = mock(Person.class);
        when(repository.find(id)).thenReturn(mockedPerson);
        service.removePerson(id);
        verify(repository).find(id);
        verify(addressService).clearAddressesFromPerson(id);
        verify(repository).remove(id);
    }

    @Test
    void shouldListEveryPersonDelegatingTasksToRepositoryAndAddressService() {
        PersonIdentifier id = DatabaseIdentifier.of(999);
        Person mockedPerson = mock(Person.class);
        when(repository.listAll()).thenReturn(List.of(mockedPerson, mockedPerson, mockedPerson));
        when(mockedPerson.identifier()).thenReturn(id);
        service.listEveryPerson();
        verify(repository).listAll();
        verify(addressService, times(3)).listAddressesFor(id);
    }
}