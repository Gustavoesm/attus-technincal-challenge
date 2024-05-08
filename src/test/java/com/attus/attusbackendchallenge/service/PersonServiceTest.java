package com.attus.attusbackendchallenge.service;

import com.attus.attusbackendchallenge.model.*;
import com.attus.attusbackendchallenge.model.repositories.PersonRepository;
import com.attus.attusbackendchallenge.service.dto.PersonDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static com.attus.attusbackendchallenge.fixtures.IntegrationTestsFixture.aPersonDto;
import static com.attus.attusbackendchallenge.fixtures.IntegrationTestsFixture.aPersonWithAnAddress;
import static com.attus.attusbackendchallenge.service.helpers.PersonServiceHelper.toPerson;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        PersonDto dto = aPersonDto();
        when(repository.add(any())).thenReturn(aPersonWithAnAddress());
        Person returnedPerson = service.addPerson(dto);
        verify(repository).add(toPerson(dto));
        assertEquals(aPersonWithAnAddress().value(), returnedPerson.identifier().value());
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
        PersonDto dto = aPersonDto();
        Person mockedPerson = mock(Person.class);
        when(repository.find(id)).thenReturn(mockedPerson);
        service.updatePerson(id, dto);
        verify(repository).find(id);
        verify(mockedPerson).setFirstName(new PersonName(dto.firstName()));
        verify(mockedPerson).setLastName(new PersonName(dto.lastName()));
        verify(mockedPerson).setBirthDate(new BirthDate(dto.birthDate()));
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
        PersonIdentifier id = new DatabaseIdentifier(999);
        Person mockedPerson = mock(Person.class);
        Address mockedAddress = mock(Address.class);
        when(repository.listAll()).thenReturn(List.of(mockedPerson, mockedPerson, mockedPerson));
        when(mockedPerson.identifier()).thenReturn(id);
        service.listEveryPerson();
        verify(repository).listAll();
        verify(addressService, times(3)).listAddressesFor(id);
    }
}