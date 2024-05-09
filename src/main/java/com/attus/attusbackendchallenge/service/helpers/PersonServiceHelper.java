package com.attus.attusbackendchallenge.service.helpers;

import com.attus.attusbackendchallenge.controller.dto.PersonDto;
import com.attus.attusbackendchallenge.model.BirthDate;
import com.attus.attusbackendchallenge.model.Person;
import com.attus.attusbackendchallenge.model.PersonName;

import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

public class PersonServiceHelper {

    public static void updateWithNonNullAttributes(Person toReplace, Person newValues) {
        updateFirstNameIfNotNull(toReplace, newValues);
        updateLastNameIfNotNull(toReplace, newValues);
        updateBirthDayIfNotNull(toReplace, newValues);
    }

    public static void updateFirstNameIfNotNull(Person toReplace, Person newValues) {
        if (!isNull(newValues.firstName())) {
            toReplace.setFirstName(newValues.firstName());
        }
    }

    public static void updateLastNameIfNotNull(Person toReplace, Person newValues) {
        if (!isNull(newValues.lastName())) {
            toReplace.setLastName(newValues.lastName());
        }
    }

    public static void updateBirthDayIfNotNull(Person toReplace, Person newValues) {
        if (!isNull(newValues.birthDate())) {
            toReplace.setBirthDate(newValues.birthDate());
        }
    }

    public static boolean isMissingRequiredField(Person person) {
        return Stream.of(person.firstName(), person.lastName(), person.birthDate())
                .anyMatch(Objects::isNull);
    }

    public static PersonDto toDto(Person person) {
        return new PersonDto(
                Integer.parseInt(person.identifier().value()),
                person.firstName().value(),
                person.lastName().value(),
                person.birthDate().value(),
                AddressServiceHelper.toDtoGroup(person.addresses())
        );
    }

    public static Person toPerson(PersonDto dto) {
        PersonName firstName = ofNullable(dto.firstName()).map(PersonName::new).orElse(null);
        PersonName lastName = ofNullable(dto.lastName()).map(PersonName::new).orElse(null);
        BirthDate birthDate = ofNullable(dto.birthDate()).map(BirthDate::new).orElse(null);
        return new Person(firstName, lastName, birthDate);
    }
}
