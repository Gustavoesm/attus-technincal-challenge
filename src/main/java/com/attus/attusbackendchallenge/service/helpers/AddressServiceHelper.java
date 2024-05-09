package com.attus.attusbackendchallenge.service.helpers;

import com.attus.attusbackendchallenge.controller.dto.AddressDto;
import com.attus.attusbackendchallenge.controller.dto.PersonAddressesDto;
import com.attus.attusbackendchallenge.model.*;

import java.util.List;

import static java.util.Objects.isNull;

public class AddressServiceHelper {
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
