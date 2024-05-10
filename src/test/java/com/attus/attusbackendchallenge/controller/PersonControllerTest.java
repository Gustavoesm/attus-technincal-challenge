package com.attus.attusbackendchallenge.controller;

import com.attus.attusbackendchallenge.controller.dto.PersonDto;
import com.attus.attusbackendchallenge.model.DatabaseIdentifier;
import com.attus.attusbackendchallenge.model.Person;
import com.attus.attusbackendchallenge.model.PersonIdentifier;
import com.attus.attusbackendchallenge.service.PersonService;
import com.attus.attusbackendchallenge.service.helpers.PersonServiceHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

import static com.attus.attusbackendchallenge.fixtures.PersonAddressFixture.aPersonAddresses;
import static com.attus.attusbackendchallenge.fixtures.PersonFixture.aPerson;
import static com.attus.attusbackendchallenge.fixtures.PersonFixture.anotherPerson;
import static com.attus.attusbackendchallenge.service.helpers.PersonServiceHelper.toDto;
import static com.attus.attusbackendchallenge.service.helpers.PersonServiceHelper.toPerson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class PersonControllerTest {

    @Autowired
    private PersonController controller;

    @MockBean
    private PersonService service;

    @Test
    void shouldDelegateListingToService() {
        Person personWithAddress = aPerson();
        Person personWithNoAddress = anotherPerson();
        personWithAddress.setAddresses(aPersonAddresses());
        personWithAddress.setIdentifier(DatabaseIdentifier.of(1L));
        personWithNoAddress.setIdentifier(DatabaseIdentifier.of(2L));
        List<Person> everyPerson = List.of(personWithAddress, personWithNoAddress, personWithNoAddress);
        when(service.listEveryPerson()).thenReturn(everyPerson);
        ResponseEntity<List<PersonDto>> response = controller.listAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(everyPerson.stream().map(PersonServiceHelper::toDto).toList(), response.getBody());
    }

    @Test
    void shouldDelegateGetToService() {
        Person person = aPerson();
        PersonIdentifier personId = DatabaseIdentifier.of(1L);
        person.setIdentifier(personId);
        when(service.getPerson(personId)).thenReturn(person);
        ResponseEntity<PersonDto> response = controller.getPerson(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(toDto(person), response.getBody());
    }

    @Test
    void shouldDelegateCreateToService() {
        PersonDto dto = new PersonDto(null, "Robert", "Lewandowski", new Date(588135600000L), null);
        Person person = aPerson();
        person.setIdentifier(DatabaseIdentifier.of(1L));
        when(service.addPerson(toPerson(dto))).thenReturn(person);
        ResponseEntity<PersonDto> response = controller.createPerson(dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(toDto(person), response.getBody());
    }

    @Test
    void shouldDelegateUpdateToService() {
        PersonDto dto = new PersonDto(null, "Robert", "Lewandowski", new Date(588135600000L), null);
        Person person = aPerson();
        PersonIdentifier personId = DatabaseIdentifier.of(1L);
        person.setIdentifier(personId);
        when(service.updatePerson(personId, toPerson(dto))).thenReturn(person);
        ResponseEntity<PersonDto> response = controller.updatePerson(1L, dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(toDto(person), response.getBody());
    }

    @Test
    void shouldDelegateDeleteToService() {
        Person person = aPerson();
        PersonIdentifier personId = DatabaseIdentifier.of(1L);
        person.setIdentifier(personId);
        when(service.removePerson(personId)).thenReturn(person);
        ResponseEntity<PersonDto> response = controller.deletePerson(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(toDto(person), response.getBody());
    }
}