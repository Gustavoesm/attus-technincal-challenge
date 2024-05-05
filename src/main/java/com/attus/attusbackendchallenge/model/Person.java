package com.attus.attusbackendchallenge.model;

public class Person {
    private PersonIdentifier identifier;
    private PersonName firstName;
    private PersonName lastName;
    private BirthDate birthDate;
    private final PersonAddresses addresses;

    public Person(PersonName firstName, PersonName lastName, BirthDate birthDate, PersonAddresses addresses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.addresses = addresses;
    }

    public PersonIdentifier identifier() {
        return identifier;
    }

    public PersonName firstName() {
        return firstName;
    }

    public PersonName lastName() {
        return lastName;
    }

    public BirthDate birthDate() {
        return birthDate;
    }

    public PersonAddresses addresses() {
        return addresses;
    }

    public void setIdentifier(PersonIdentifier identifier) {
        this.identifier = identifier;
    }

    public void setFirstName(PersonName firstName) {
        this.firstName = firstName;
    }

    public void setLastName(PersonName lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(BirthDate birthDate) {
        this.birthDate = birthDate;
    }


}
