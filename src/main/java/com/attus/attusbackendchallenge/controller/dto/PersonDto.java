package com.attus.attusbackendchallenge.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@JsonInclude(Include.NON_NULL)
public record PersonDto(
        Integer id,
        String firstName,
        String lastName,
        @JsonFormat(shape = STRING, pattern = "dd-MM-yyyy")
        Date birthDate,
        PersonAddressesDto addresses
) {
}
