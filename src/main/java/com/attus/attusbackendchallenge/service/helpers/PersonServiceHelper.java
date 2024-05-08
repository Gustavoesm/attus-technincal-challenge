package com.attus.attusbackendchallenge.service.helpers;

import com.attus.attusbackendchallenge.model.BirthDate;
import com.attus.attusbackendchallenge.model.Person;
import com.attus.attusbackendchallenge.model.PersonName;
import com.attus.attusbackendchallenge.service.dto.PersonDto;

import static java.util.Objects.isNull;

public class PersonServiceHelper {

    public static void updateWithNonNullAttributes(Person toReplace, PersonDto newValues) {
        updateFirstNameIfNotNull(toReplace, newValues);
        updateLastNameIfNotNull(toReplace, newValues);
        updateBirthDayIfNotNull(toReplace, newValues);
    }

    public static void updateFirstNameIfNotNull(Person toReplace, PersonDto newValues) {
        if (!isNull(newValues.firstName())) {
            toReplace.setFirstName(new PersonName(newValues.firstName()));
        }
    }

    public static void updateLastNameIfNotNull(Person toReplace, PersonDto newValues) {
        if (!isNull(newValues.lastName())) {
            toReplace.setLastName(new PersonName(newValues.lastName()));
        }
    }

    public static void updateBirthDayIfNotNull(Person toReplace, PersonDto newValues) {
        if (!isNull(newValues.birthDate())) {
            toReplace.setBirthDate(new BirthDate(newValues.birthDate()));
        }
    }

    public static PersonDto toDto(Person person) {
        return new PersonDto(
                Integer.parseInt(person.identifier().value()),
                person.firstName().value(),
                person.lastName().value(),
                person.birthDate().value(),
                person.addresses().listAll().stream().map(AddressServiceHelper::toDto).toList(),
                AddressServiceHelper.toDto(person.addresses().getMainAddress())
        );
    }

    public static Person toPerson(PersonDto dto) {
        return new Person(
                new PersonName(dto.firstName()),
                new PersonName(dto.lastName()),
                new BirthDate(dto.birthDate())
        );
    }
}
