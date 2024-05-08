package com.attus.attusbackendchallenge.service.helpers;

import com.attus.attusbackendchallenge.model.Address;
import com.attus.attusbackendchallenge.service.dto.AddressDto;

public class AddressServiceHelper {
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
