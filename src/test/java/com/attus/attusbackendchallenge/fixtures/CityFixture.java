package com.attus.attusbackendchallenge.fixtures;

import com.attus.attusbackendchallenge.model.City;

public class CityFixture {
    public static City aCity() {
        return new City("São Paulo");
    }

    public static City anotherCity() {
        return new City("Porto Alegre");
    }

    public static City aNewCity() {
        return new City("Fortaleza");
    }

    public static City aSecondNewCity() {
        return new City("Rio de Janeiro");
    }
}
