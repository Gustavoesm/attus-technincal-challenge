package com.attus.attusbackendchallenge.infra.jdbc;

import com.attus.attusbackendchallenge.infra.exceptions.PersonNotFoundException;
import com.attus.attusbackendchallenge.infra.helpers.AddressRepositoryHelper.PersonAddressesRowMapper;
import com.attus.attusbackendchallenge.model.*;
import com.attus.attusbackendchallenge.model.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import static com.attus.attusbackendchallenge.infra.helpers.AddressRepositoryHelper.namedParameters;

@Repository
public class JdbcAddressRepository implements AddressRepository {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<PersonAddresses> personAddressesRowMapper = new PersonAddressesRowMapper();

    @Override
    public Address add(PersonIdentifier identifier, Address address, boolean isMainAddress) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String insertSQL = "INSERT INTO address (person_id, is_main_address, postal_code, state, city, " +
                "street, number) VALUES (:person_id, :is_main_address, :postal_code, :state, :city, :street, :number)";
        try {
            jdbcTemplate.update(insertSQL, namedParameters(identifier, address, isMainAddress), keyHolder);
            address.setIdentifier(new DatabaseIdentifier(keyHolder.getKey().longValue()));
            return address;
        } catch (DataIntegrityViolationException e) {
            throw new PersonNotFoundException("There was a problem while attempting to associate this address to a person.", e)
                    ;
        }
    }

    @Override
    public PersonAddresses personAddresses(PersonIdentifier identifier) {
        String listAddressSQL = "SELECT * FROM address WHERE person_id = :person_id";
        try {
            return jdbcTemplate.queryForObject(listAddressSQL, namedParameters(identifier), personAddressesRowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new PersonNotFoundException("No addresses found for person (%s).".formatted(identifier.value()));
        }
    }

    @Override
    public AddressIdentifier findIndexOf(PersonIdentifier identifier, Address address) {
        String findSQL = "SELECT id FROM address WHERE person_id = :person_id AND postal_code = :postal_code AND " +
                "state = :state AND city = :city AND street = :street AND number = :number";
        try {
            return new DatabaseIdentifier(jdbcTemplate.queryForObject(findSQL, namedParameters(identifier, address), Long.class));
        } catch (EmptyResultDataAccessException | NullPointerException e) {
            return null;
        }

    }

    @Override
    public boolean replaceAddress(AddressIdentifier id, Address newAddress) {
        String replaceSQL = "UPDATE address SET postal_code = :postal_code, state = :state, city = :city, " +
                "street = :street, number = :number WHERE id = :id";
        return jdbcTemplate.update(replaceSQL, namedParameters(newAddress, id)) > 0;
    }

    @Override
    public boolean remove(AddressIdentifier id) {
        String removeSQL = "DELETE FROM address WHERE id = :id";
        return jdbcTemplate.update(removeSQL, namedParameters(id)) > 0;
    }
}
