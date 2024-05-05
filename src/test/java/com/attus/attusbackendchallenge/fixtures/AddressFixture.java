package com.attus.attusbackendchallenge.fixtures;

import com.attus.attusbackendchallenge.model.Address;
import com.attus.attusbackendchallenge.model.BrazilianStates;

import static com.attus.attusbackendchallenge.fixtures.BrazilianCEPFixture.*;
import static com.attus.attusbackendchallenge.fixtures.CityFixture.*;
import static com.attus.attusbackendchallenge.fixtures.ResidenceIdentifierFixture.*;
import static com.attus.attusbackendchallenge.fixtures.StreetNameFixture.*;

public class AddressFixture {

    public static Address anAddress() {
        return new Address(
                aBrazilianCEP(),
                aCity(),
                BrazilianStates.SAO_PAULO,
                aStreet(),
                aResidenceIdentifier()
        );
    }

    public static Address anotherAddress() {
        return new Address(
                anotherBrazilianCEP(),
                anotherCity(),
                BrazilianStates.RIO_GRANDE_DO_SUL,
                anotherStreet(),
                anotherResidenceIdentifier()
        );
    }

    public static Address aNewAddress() {
        return new Address(
                aNewBrazilianCEP(),
                aNewCity(),
                BrazilianStates.CEARA,
                aNewStreet(),
                aNewResidenceIdentifier()
        );
    }

    public static Address aSecondNewAddress() {
        return new Address(
                aSecondNewBrazilianCEP(),
                aSecondNewCity(),
                BrazilianStates.RIO_DE_JANEIRO,
                aSecondNewStreet(),
                aSecondNewResidenceIdentifier()
        );
    }
}
