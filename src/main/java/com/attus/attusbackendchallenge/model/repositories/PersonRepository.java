package com.attus.attusbackendchallenge.model.repositories;

import com.attus.attusbackendchallenge.model.Person;
import com.attus.attusbackendchallenge.model.PersonIdentifier;

public interface PersonRepository {
    boolean add(Person person);

    Person find(PersonIdentifier identifier);

    boolean replace(PersonIdentifier identifier, Person person);

    boolean remove(PersonIdentifier identifier);
}
