package com.attus.attusbackendchallenge.infra.jdbc;

import com.attus.attusbackendchallenge.infra.exceptions.PersonNotFoundException;
import com.attus.attusbackendchallenge.model.Person;
import com.attus.attusbackendchallenge.model.PersonIdentifier;
import com.attus.attusbackendchallenge.model.repositories.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static com.attus.attusbackendchallenge.fixtures.IntegrationTestsFixture.*;
import static com.attus.attusbackendchallenge.fixtures.PersonFixture.*;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Sql(scripts = {"classpath:dataSetup.sql"})
@ComponentScan
class PersonRepositoryTest {

    @Autowired
    private PersonRepository repository;

    @Test
    void shouldCorrectlyAddANewPersonAndReturnId() {
        PersonIdentifier id = repository.add(aNewPerson());
        assertEquals("1", id.value());
    }

    @Test
    void shouldCorrectlyRecoverPerson() {
        Person recoveredPerson = repository.find(aPersonWithAnAddress());
        assertEquals(aPerson().firstName(), recoveredPerson.firstName());
        assertEquals(aPerson().lastName(), recoveredPerson.lastName());
        assertEquals(aPerson().birthDate(), recoveredPerson.birthDate());
    }

    @Test
    void shouldThrowWhenAttemptingToRecoverNonExistentPerson() {
        assertThrows(PersonNotFoundException.class, () -> repository.find(aNonExistentPerson()));
    }

    @Test
    void shouldCorrectlyReplaceAPerson() {
        repository.replace(aPersonWithAnAddress(), aNewPerson());
        Person recoveredPerson = repository.find(aPersonWithAnAddress());
        assertEquals(aNewPerson().firstName(), recoveredPerson.firstName());
        assertEquals(aNewPerson().lastName(), recoveredPerson.lastName());
        assertEquals(aNewPerson().birthDate(), recoveredPerson.birthDate());
    }

    @Test
    void shouldReturnFalseWhenUnableToReplaceAPerson() {
        assertFalse(repository.replace(aNonExistentPerson(), aNewPerson()));
    }

    @Test
    void shouldCorrectlyRemoveAPerson() {
        repository.remove(aPersonWithNoAddress());
        assertThrows(PersonNotFoundException.class, () -> repository.find(aPersonWithNoAddress()));
    }

    @Test
    void shouldReturnFalseWhenUnableToRemoveAPerson() {
        assertFalse(repository.remove(aNonExistentPerson()));
    }

    @Test
    void shouldCorrectlyListAllPersons() {
        List<Person> list = repository.listAll();
        assertEquals(aPerson().firstName(), list.get(0).firstName());
        assertEquals(aPerson().lastName(), list.get(0).lastName());
        assertEquals(aPerson().birthDate(), list.get(0).birthDate());
        assertEquals("100", list.get(0).identifier().value());

        assertEquals(anotherPerson().firstName(), list.get(1).firstName());
        assertEquals(anotherPerson().lastName(), list.get(1).lastName());
        assertEquals(anotherPerson().birthDate(), list.get(1).birthDate());
        assertEquals("200", list.get(1).identifier().value());
    }
}