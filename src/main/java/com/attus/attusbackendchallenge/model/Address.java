package com.attus.attusbackendchallenge.model;

import java.util.Objects;

public class Address {
    private PostalCodeFormat postalCode;
    private State state;
    private City city;
    private ResidenceStreet street;
    private ResidenceIdentifier number;

    public Address(PostalCodeFormat postalCode, State state, City city, ResidenceStreet street, ResidenceIdentifier number) {
        this.postalCode = postalCode;
        this.state = state;
        this.city = city;
        this.street = street;
        this.number = number;
    }

    public PostalCodeFormat postalCode() {
        return postalCode;
    }

    public City city() {
        return city;
    }

    public State state() {
        return state;
    }

    public ResidenceStreet street() {
        return street;
    }

    public ResidenceIdentifier number() {
        return number;
    }

    public void setPostalCode(PostalCodeFormat postalCode) {
        this.postalCode = postalCode;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setStreet(ResidenceStreet street) {
        this.street = street;
    }

    public void setNumber(ResidenceIdentifier number) {
        this.number = number;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address address)) return false;
        return Objects.equals(postalCode, address.postalCode) && Objects.equals(city, address.city) && Objects.equals(state, address.state) && Objects.equals(street, address.street) && Objects.equals(number, address.number);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(postalCode, city, state, street, number);
    }
}
