package com.attus.attusbackendchallenge.service.dto;

public record AddressDto(
        Integer id,
        String postalCode,
        String state,
        String city,
        String street,
        String number
) {
}
