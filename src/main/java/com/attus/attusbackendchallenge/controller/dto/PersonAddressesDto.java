package com.attus.attusbackendchallenge.controller.dto;

import java.util.List;

public record PersonAddressesDto(
        List<AddressDto> all,
        AddressDto mainAddress
) {
}
