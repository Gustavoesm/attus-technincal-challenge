package com.attus.attusbackendchallenge.service;

import com.attus.attusbackendchallenge.model.Person;
import com.attus.attusbackendchallenge.model.PersonAddresses;
import com.attus.attusbackendchallenge.model.PersonIdentifier;
import com.attus.attusbackendchallenge.model.repositories.PersonRepository;
import com.attus.attusbackendchallenge.service.dto.PersonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.attus.attusbackendchallenge.service.helpers.PersonServiceHelper.toPerson;
import static com.attus.attusbackendchallenge.service.helpers.PersonServiceHelper.updateWithNonNullAttributes;

@Service
@Transactional
public class PersonService {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private AddressService addressService;

    public Person addPerson(PersonDto personDto) {
        Person person = toPerson(personDto);
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

    public Person updatePerson(PersonIdentifier id, PersonDto personDto) {
        Person person = repository.find(id);
        PersonAddresses addresses = addressService.listAddressesFor(id);
        person.setAddresses(addresses);
        updateWithNonNullAttributes(person, personDto);
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
