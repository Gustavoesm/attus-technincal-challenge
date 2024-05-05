package com.attus.attusbackendchallenge.fixtures;

import com.attus.attusbackendchallenge.model.Person;

import static com.attus.attusbackendchallenge.fixtures.BirthDateFixture.aBirthDate;
import static com.attus.attusbackendchallenge.fixtures.BirthDateFixture.anotherBirthDate;
import static com.attus.attusbackendchallenge.fixtures.PersonAddressFixture.aPersonAddresses;
import static com.attus.attusbackendchallenge.fixtures.PersonAddressFixture.anotherPersonAddresses;
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
}
