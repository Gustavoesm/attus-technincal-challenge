package com.attus.attusbackendchallenge.fixtures;

import com.attus.attusbackendchallenge.model.PersonAddresses;

import java.util.ArrayList;
import java.util.List;

import static com.attus.attusbackendchallenge.fixtures.AddressFixture.anAddress;

public class PersonAddressFixture {
    public static PersonAddresses aPersonAddresses() {
        return new PersonAddresses(new ArrayList<>(List.of(anAddress())), 0);
    }

    public static PersonAddresses anotherPersonAddresses() {
        return new PersonAddresses(new ArrayList<>(List.of(anAddress())), 0);
    }
}
