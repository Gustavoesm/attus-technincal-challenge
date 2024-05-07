package com.attus.attusbackendchallenge.model;

import java.util.Objects;

public class Person {
    private PersonIdentifier identifier;
    private PersonName firstName;
    private PersonName lastName;
    private BirthDate birthDate;
    private PersonAddresses addresses;

    public Person(PersonName firstName, PersonName lastName, BirthDate birthDate, PersonAddresses addresses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.addresses = addresses;
    }

    public Person(PersonName firstName, PersonName lastName, BirthDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
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

    public void setAddresses(PersonAddresses addresses) {
        this.addresses = addresses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(birthDate, person.birthDate) && Objects.equals(addresses, person.addresses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, birthDate, addresses);
    }
}
