package com.attus.attusbackendchallenge.fixtures;

import com.attus.attusbackendchallenge.controller.dto.AddressDto;
import com.attus.attusbackendchallenge.model.AddressIdentifier;
import com.attus.attusbackendchallenge.model.DatabaseIdentifier;
import com.attus.attusbackendchallenge.model.PersonIdentifier;

public class IntegrationTestsFixture {
    public static PersonIdentifier aPersonWithAnAddress() {
        return DatabaseIdentifier.of(100);
    }

    public static PersonIdentifier aPersonWithNoAddress() {
        return DatabaseIdentifier.of(200);
    }

    public static PersonIdentifier aNonExistentPerson() {
        return DatabaseIdentifier.of(-1);
    }

    public static AddressIdentifier anAddressId() {
        return DatabaseIdentifier.of(99);
    }

    public static AddressIdentifier aNonExistentAddressId() {
        return DatabaseIdentifier.of(-1);
    }

    public static AddressDto aRandomDto() {
        return new AddressDto(null, "12345-777", "AC", "city", "street", "8A");
    }
}
