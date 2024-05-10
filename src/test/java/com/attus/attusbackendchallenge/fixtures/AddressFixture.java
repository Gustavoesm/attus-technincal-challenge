package com.attus.attusbackendchallenge.fixtures;

import com.attus.attusbackendchallenge.model.Address;
import com.attus.attusbackendchallenge.model.BrazilianStates;

import static com.attus.attusbackendchallenge.fixtures.BrazilianCEPFixture.*;
import static com.attus.attusbackendchallenge.fixtures.CityFixture.*;
import static com.attus.attusbackendchallenge.fixtures.IntegrationTestsFixture.anAddressId;
import static com.attus.attusbackendchallenge.fixtures.ResidenceIdentifierFixture.*;
import static com.attus.attusbackendchallenge.fixtures.StreetNameFixture.*;

public class AddressFixture {

    public static Address anAddress() {
        Address address = new Address(
                aBrazilianCEP(),
                BrazilianStates.SAO_PAULO,
                aCity(),
                aStreet(),
                aNumber()
        );
        address.setIdentifier(anAddressId());
        return address;
    }

    public static Address anotherAddress() {
        return new Address(
                anotherBrazilianCEP(),
                BrazilianStates.RIO_GRANDE_DO_SUL,
                anotherCity(),
                anotherStreet(),
                anotherNumber()
        );
    }

    public static Address aNewAddress() {
        return new Address(
                aNewBrazilianCEP(),
                BrazilianStates.CEARA,
                aNewCity(),
                aNewStreet(),
                aNewNumber()
        );
    }

    public static Address aSecondNewAddress() {
        return new Address(
                aSecondNewBrazilianCEP(),
                BrazilianStates.RIO_DE_JANEIRO,
                aSecondNewCity(),
                aSecondNewStreet(),
                aSecondNewResidenceIdentifier()
        );
    }
}
