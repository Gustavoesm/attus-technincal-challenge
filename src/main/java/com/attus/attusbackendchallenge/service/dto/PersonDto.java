package com.attus.attusbackendchallenge.service.dto;

import java.util.Date;
import java.util.List;

public record PersonDto(
        Integer id,
        String firstName,
        String lastName,
        Date birthDate,
        List<AddressDto> addresses,
        AddressDto mainAddress
) {
}
