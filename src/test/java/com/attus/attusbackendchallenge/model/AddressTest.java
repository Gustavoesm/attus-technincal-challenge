package com.attus.attusbackendchallenge.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static com.attus.attusbackendchallenge.fixtures.AddressFixture.anAddress;
import static com.attus.attusbackendchallenge.fixtures.BrazilianCEPFixture.aBrazilianCEP;
import static com.attus.attusbackendchallenge.fixtures.BrazilianCEPFixture.anotherBrazilianCEP;
import static com.attus.attusbackendchallenge.fixtures.CityFixture.aCity;
import static com.attus.attusbackendchallenge.fixtures.CityFixture.anotherCity;
import static com.attus.attusbackendchallenge.fixtures.ResidenceIdentifierFixture.aResidenceIdentifier;
import static com.attus.attusbackendchallenge.fixtures.ResidenceIdentifierFixture.anotherResidenceIdentifier;
import static com.attus.attusbackendchallenge.fixtures.StreetNameFixture.aStreet;
import static com.attus.attusbackendchallenge.fixtures.StreetNameFixture.anotherStreet;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressTest {

    @Test
    void shouldCorrectlyTestIdentifier() {
        Address address = anAddress();
        AddressIdentifier identifier = DatabaseIdentifier.of(1);
        address.setIdentifier(identifier);
        assertEquals(identifier, address.identifier());
    }

    @Test
    void shouldCorrectlyRecoverPostalCode() {
        assertEquals(aBrazilianCEP().value(), anAddress().postalCode().value());
    }

    @Test
    void shouldCorrectlyRecoverCity() {
        assertEquals(aCity().name(), anAddress().city().name());
    }

    @Test
    void shouldCorrectlyRecoverState() {
        assertEquals(BrazilianStates.SAO_PAULO, anAddress().state());
    }

    @Test
    void shouldCorrectlyRecoverStreetName() {
        assertEquals(aStreet().name(), anAddress().street().name());
    }

    @Test
    void shouldCorrectlyRecoverResidenceIdentifier() {
        assertEquals(aResidenceIdentifier().value(), anAddress().number().value());
    }

    @Test
    void shouldCorrectlyUpdatePostalCode() {
        assertEquals(aBrazilianCEP().value(), anAddress().postalCode().value());
        Address updatedAddress = anAddress();
        updatedAddress.setPostalCode(anotherBrazilianCEP());
        assertEquals(anotherBrazilianCEP().value(), updatedAddress.postalCode().value());
    }

    @Test
    void shouldCorrectlyUpdateCity() {
        assertEquals(aCity().name(), anAddress().city().name());
        Address updatedAddress = anAddress();
        updatedAddress.setCity(anotherCity());
        assertEquals(anotherCity().name(), updatedAddress.city().name());
    }

    @Test
    void shouldCorrectlyUpdateState() {
        assertEquals(BrazilianStates.SAO_PAULO, anAddress().state());
        Address updatedAddress = anAddress();
        updatedAddress.setState(BrazilianStates.RIO_GRANDE_DO_SUL);
        assertEquals(BrazilianStates.RIO_GRANDE_DO_SUL, updatedAddress.state());
    }

    @Test
    void shouldCorrectlyUpdateStreetName() {
        assertEquals(aStreet().name(), anAddress().street().name());
        Address updatedAddress = anAddress();
        updatedAddress.setStreet(anotherStreet());
        assertEquals(anotherStreet().name(), updatedAddress.street().name());
    }

    @Test
    void shouldCorrectlyUpdateResidenceIdentifier() {
        assertEquals(aResidenceIdentifier().value(), anAddress().number().value());
        Address updatedAddress = anAddress();
        updatedAddress.setNumber(anotherResidenceIdentifier());
        assertEquals(anotherResidenceIdentifier().value(), updatedAddress.number().value());
    }

    @Test
    void verifyEqualsContract() {
        EqualsVerifier.simple().forClass(Address.class).withIgnoredFields("identifier").verify();
    }
}