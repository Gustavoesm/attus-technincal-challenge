package com.attus.attusbackendchallenge.fixtures;

import com.attus.attusbackendchallenge.model.AddressIdentifier;
import com.attus.attusbackendchallenge.model.DatabaseIdentifier;
import com.attus.attusbackendchallenge.model.PersonIdentifier;
import com.attus.attusbackendchallenge.service.dto.PersonDto;

import java.util.Date;

public class IntegrationTestsFixture {
    public static PersonIdentifier aPersonWithAnAddress() {
        return new DatabaseIdentifier(100);
    }

    public static PersonIdentifier aPersonWithNoAddress() {
        return new DatabaseIdentifier(200);
    }

    public static PersonIdentifier aNonExistentPerson() {
        return new DatabaseIdentifier(-1);
    }

    public static AddressIdentifier anAddressId() {
        return new DatabaseIdentifier(99);
    }

    public static AddressIdentifier aNonExistentAddressId() {
        return new DatabaseIdentifier(-1);
    }

    public static PersonDto aPersonDto() {
        return new PersonDto(null, "Julian", "Brandt", new Date(831006000000L), null, null);
    }
}
