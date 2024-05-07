package com.attus.attusbackendchallenge.fixtures;

import com.attus.attusbackendchallenge.model.Person;

import static com.attus.attusbackendchallenge.fixtures.BirthDateFixture.*;
import static com.attus.attusbackendchallenge.fixtures.PersonAddressFixture.*;
import static com.attus.attusbackendchallenge.fixtures.PersonNameFixture.*;

public class PersonFixture {
    public static Person aPerson() {
        return new Person(
                aFirstName(),
                aLastName(),
                aBirthDate(),
                aPersonAddresses()
        );
    }

    public static Person anotherPerson() {
        return new Person(
                anotherFirstName(),
                anotherLastName(),
                anotherBirthDate(),
                anotherPersonAddresses()
        );
    }

    public static Person aNewPerson() {
        return new Person(
                aNewFirstName(),
                aNewLastName(),
                aNewBirthDate(),
                aNewPersonAddresses()
        );
    }
}
