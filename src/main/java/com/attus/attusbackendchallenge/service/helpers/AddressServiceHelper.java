package com.attus.attusbackendchallenge.service.helpers;

import com.attus.attusbackendchallenge.controller.dto.AddressDto;
import com.attus.attusbackendchallenge.controller.dto.PersonAddressesDto;
import com.attus.attusbackendchallenge.model.*;

import java.util.List;

import static java.util.Objects.isNull;

public class AddressServiceHelper {
    public static void updateWithNonNullAttributes(Address toReplace, Address newValues) {
        updatePostalCodeIfNotNull(toReplace, newValues);
        updateStateIfNotNull(toReplace, newValues);
        updateCityIfNotNull(toReplace, newValues);
        updateStreetIfNotNull(toReplace, newValues);
        updateNumberIfNotNull(toReplace, newValues);
    }

    public static void updatePostalCodeIfNotNull(Address toReplace, Address newValues) {
        if (!isNull(newValues.postalCode())) {
            toReplace.setPostalCode(newValues.postalCode());
        }
    }

    public static void updateStateIfNotNull(Address toReplace, Address newValues) {
        if (!isNull(newValues.state())) {
            toReplace.setState(newValues.state());
        }
    }

    public static void updateCityIfNotNull(Address toReplace, Address newValues) {
        if (!isNull(newValues.city())) {
            toReplace.setCity(newValues.city());
        }
    }

    public static void updateStreetIfNotNull(Address toReplace, Address newValues) {
        if (!isNull(newValues.street())) {
            toReplace.setStreet(newValues.street());
        }
    }

    public static void updateNumberIfNotNull(Address toReplace, Address newValues) {
        if (!isNull(newValues.number())) {
            toReplace.setNumber(newValues.number());
        }
    }

    public static Address toAddress(AddressDto dto) {
        return new Address(
                new BrazilianCEP(dto.postalCode()),
                BrazilianStates.from(dto.state()),
                new City(dto.city()),
                new ResidenceStreet(dto.street()),
                new ResidenceIdentifier(dto.number())
        );
    }

    public static PersonAddressesDto toDtoGroup(PersonAddresses addresses) {
        if (isNull(addresses)) {
            return null;
        }
        List<AddressDto> addressDtos = addresses.listAll().stream().map(AddressServiceHelper::toDto).toList();
        AddressDto mainAddressDto = toDto(addresses.getMainAddress());
        return new PersonAddressesDto(addressDtos, mainAddressDto);
    }

    public static AddressDto toDto(Address address) {
        return new AddressDto(
                Integer.parseInt(address.identifier().value()),
                address.postalCode().value(),
                address.state().longName(),
                address.city().name(),
                address.street().name(),
                address.number().value()
        );
    }

}
