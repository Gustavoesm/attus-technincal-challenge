package com.attus.attusbackendchallenge.infra.jdbc;

import com.attus.attusbackendchallenge.infra.exceptions.PersonNotFoundException;
import com.attus.attusbackendchallenge.infra.helpers.PersonRepositoryHelper.PersonRowMapper;
import com.attus.attusbackendchallenge.model.DatabaseIdentifier;
import com.attus.attusbackendchallenge.model.Person;
import com.attus.attusbackendchallenge.model.PersonIdentifier;
import com.attus.attusbackendchallenge.model.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.attus.attusbackendchallenge.infra.helpers.PersonRepositoryHelper.params;

@Repository
public class JdbcPersonRepository implements PersonRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<Person> rowMapper = new PersonRowMapper();

    @Override
    public Person add(Person person) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String insertSQL = "INSERT INTO person (first_name, last_name, birth_date) VALUES (:first_name, :last_name, :birth_date)";
        jdbcTemplate.update(insertSQL, params(person), keyHolder);
        person.setIdentifier(new DatabaseIdentifier(keyHolder.getKey().longValue()));
        return person;
    }

    @Override
    public Person find(PersonIdentifier identifier) {
        String selectSQL = "SELECT * FROM person WHERE id = :id";
        try {
            return jdbcTemplate.queryForObject(selectSQL, params(identifier), rowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new PersonNotFoundException("Could not find an entry for this person.", e);
        }
    }

    @Override
    public boolean replace(PersonIdentifier identifier, Person person) {
        String replaceSQL = "UPDATE person SET first_name = :first_name, last_name = :last_name, birth_date = :birth_date WHERE id = :id";
        return jdbcTemplate.update(replaceSQL, params(identifier, person)) > 0;
    }

    @Override
    public boolean remove(PersonIdentifier identifier) {
        String removeSQL = "DELETE FROM person WHERE id = :id";
        return jdbcTemplate.update(removeSQL, params(identifier)) > 0;
    }

    @Override
    public List<Person> listAll() {
        String selectAllSQL = "SELECT * FROM person";
        List<Person> listAll = jdbcTemplate.query(selectAllSQL, rowMapper);
        return listAll;
    }
}
