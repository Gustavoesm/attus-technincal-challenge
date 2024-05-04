package com.attus.attusbackendchallenge.model;

public class Address {
    private PostalCodeFormat postalCode;
    private City city;
    private State state;
    private ResidenceStreet street;
    private ResidenceIdentifier number;

    public Address(PostalCodeFormat postalCode, City city, State state, ResidenceStreet street, ResidenceIdentifier number) {
        this.postalCode = postalCode;
        this.city = city;
        this.state = state;
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
}
