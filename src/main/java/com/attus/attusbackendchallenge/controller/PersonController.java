package com.attus.attusbackendchallenge.controller;

import com.attus.attusbackendchallenge.controller.dto.PersonDto;
import com.attus.attusbackendchallenge.model.DatabaseIdentifier;
import com.attus.attusbackendchallenge.model.Person;
import com.attus.attusbackendchallenge.model.PersonIdentifier;
import com.attus.attusbackendchallenge.service.PersonService;
import com.attus.attusbackendchallenge.service.helpers.PersonServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.attus.attusbackendchallenge.service.helpers.PersonServiceHelper.toDto;
import static com.attus.attusbackendchallenge.service.helpers.PersonServiceHelper.toPerson;

@RestController
@CrossOrigin(origins = {"http://localhost:8080"})
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService service;

    @GetMapping("/all")
    public ResponseEntity<List<PersonDto>> listAll() {
        List<Person> everyPerson = service.listEveryPerson();
        List<PersonDto> response = everyPerson.stream().map(PersonServiceHelper::toDto).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{personId}")
    public ResponseEntity<PersonDto> getPerson(@PathVariable Long personId) {
        PersonIdentifier id = DatabaseIdentifier.of(personId);
        return ResponseEntity.ok(toDto(service.getPerson(id)));
    }

    @PostMapping
    public ResponseEntity<PersonDto> createPerson(@RequestBody PersonDto body) {
        Person person = toPerson(body);
        return ResponseEntity.ok(toDto(service.addPerson(person)));
    }

    @PutMapping("/{personId}")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable Long personId, @RequestBody PersonDto body) {
        PersonIdentifier id = DatabaseIdentifier.of(personId);
        Person person = toPerson(body);
        return ResponseEntity.ok(toDto(service.updatePerson(id, person)));
    }

    @DeleteMapping("/{personId}")
    public ResponseEntity<PersonDto> deletePerson(@PathVariable Long personId) {
        PersonIdentifier id = DatabaseIdentifier.of(personId);
        return ResponseEntity.ok(toDto(service.removePerson(id)));
    }
}
