package com.attus.attusbackendchallenge.service;

import com.attus.attusbackendchallenge.infra.exceptions.InvalidPersonException;
import com.attus.attusbackendchallenge.model.Person;
import com.attus.attusbackendchallenge.model.PersonAddresses;
import com.attus.attusbackendchallenge.model.PersonIdentifier;
import com.attus.attusbackendchallenge.model.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.attus.attusbackendchallenge.service.helpers.PersonServiceHelper.isMissingRequiredField;
import static com.attus.attusbackendchallenge.service.helpers.PersonServiceHelper.updateWithNonNullAttributes;

@Service
@Transactional
public class PersonService {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private AddressService addressService;

    public Person addPerson(Person person) {
        if (isMissingRequiredField(person)) {
            throw new InvalidPersonException("Person is missing required fields");
        }
        PersonIdentifier id = repository.add(person);
        person.setIdentifier(id);
        return person;
    }

    public Person getPerson(PersonIdentifier identifier) {
        Person person = repository.find(identifier);
        PersonAddresses addresses = addressService.listAddressesFor(identifier);
        person.setAddresses(addresses);
        return person;
    }

    public Person updatePerson(PersonIdentifier id, Person newValues) {
        Person person = repository.find(id);
        PersonAddresses addresses = addressService.listAddressesFor(id);
        updateWithNonNullAttributes(person, newValues);
        person.setAddresses(addresses);
        repository.replace(id, person);
        return person;
    }

    public Person removePerson(PersonIdentifier id) {
        Person person = this.getPerson(id);
        addressService.clearAddressesFromPerson(id);
        repository.remove(id);
        return person;
    }

    public List<Person> listEveryPerson() {
        List<Person> everyPerson = repository.listAll();
        everyPerson.forEach(person -> {
            PersonAddresses addresses = addressService.listAddressesFor(person.identifier());
            person.setAddresses(addresses);
        });
        return everyPerson;
    }
}
