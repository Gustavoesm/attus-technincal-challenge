package com.attus.attusbackendchallenge.fixtures;

import com.attus.attusbackendchallenge.model.ResidenceStreet;

public class StreetNameFixture {
    public static ResidenceStreet aStreet() {
        return new ResidenceStreet("Praça Roberto Gomes Pedrosa");
    }

    public static ResidenceStreet anotherStreet() {
        return new ResidenceStreet("Av. Padre Leopoldo Brentano");
    }

    public static ResidenceStreet aNewStreet() {
        return new ResidenceStreet("Av. Alberto Craveiro");
    }

    public static ResidenceStreet aSecondNewStreet() {
        return new ResidenceStreet("Av. Roberto Dinamite");
    }
}
