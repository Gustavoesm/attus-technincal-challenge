package com.attus.attusbackendchallenge.fixtures;

import com.attus.attusbackendchallenge.model.PersonName;

public class PersonNameFixture {
    public static PersonName aFirstName() {
        return new PersonName("Julian");
    }

    public static PersonName aLastName() {
        return new PersonName("Brandt");
    }

    public static PersonName anotherFirstName() {
        return new PersonName("Mario");
    }

    public static PersonName anotherLastName() {
        return new PersonName("Gotze");
    }
}
