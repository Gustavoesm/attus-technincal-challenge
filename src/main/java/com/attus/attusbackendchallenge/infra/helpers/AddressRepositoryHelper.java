package com.attus.attusbackendchallenge.infra.helpers;

import com.attus.attusbackendchallenge.infra.exceptions.AddressDataAccessException;
import com.attus.attusbackendchallenge.model.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressRepositoryHelper {
    public static MapSqlParameterSource namedParameters(PersonIdentifier personId, Address address, boolean isMainAddress) {
        MapSqlParameterSource params = new MapSqlParameterSource("person_id", Long.parseLong(personId.value()));
        params.addValue("is_main_address", isMainAddress);
        params.addValue("postal_code", address.postalCode().value());
        params.addValue("state", address.state().toString());
        params.addValue("city", address.city().name());
        params.addValue("street", address.street().name());
        params.addValue("number", address.number().value());
        return params;
    }

    public static MapSqlParameterSource namedParameters(Address address, AddressIdentifier id) {
        MapSqlParameterSource params = new MapSqlParameterSource("id", Long.parseLong(id.value()));
        params.addValue("postal_code", address.postalCode().value());
        params.addValue("state", address.state().toString());
        params.addValue("city", address.city().name());
        params.addValue("street", address.street().name());
        params.addValue("number", address.number().value());
        return params;
    }

    public static MapSqlParameterSource namedParameters(PersonIdentifier personId, Address address) {
        MapSqlParameterSource params = new MapSqlParameterSource("person_id", Long.parseLong(personId.value()));
        params.addValue("postal_code", address.postalCode().value());
        params.addValue("state", address.state().toString());
        params.addValue("city", address.city().name());
        params.addValue("street", address.street().name());
        params.addValue("number", address.number().value());
        return params;
    }

    public static MapSqlParameterSource namedParameters(AddressIdentifier id) {
        return new MapSqlParameterSource("id", Long.parseLong(id.value()));
    }

    public static MapSqlParameterSource namedParameters(PersonIdentifier personId) {
        return new MapSqlParameterSource("person_id", Long.parseLong(personId.value()));
    }

    public static class PersonAddressesRowMapper implements RowMapper<PersonAddresses> {
        @Override
        public PersonAddresses mapRow(ResultSet rs, int rowNum) throws SQLException {
            List<Address> addressList = new ArrayList<>();
            int mainAddressIndex = -1;
            int row = 0;
            do {
                addressList.add(mapAddressFrom(rs));
                if (rs.getBoolean("is_main_address")) {
                    mainAddressIndex = row;
                }
                row++;
            } while (rs.next());

            return new PersonAddresses(addressList, mainAddressIndex);
        }

        private Address mapAddressFrom(ResultSet rs) {
            try {
                return new Address(
                        new BrazilianCEP(rs.getString("postal_code")),
                        BrazilianStates.valueOf(rs.getString("state")),
                        new City(rs.getString("city")),
                        new ResidenceStreet(rs.getString("street")),
                        new ResidenceIdentifier(rs.getString("number"))
                );
            } catch (SQLException | IllegalArgumentException e) {
                throw new AddressDataAccessException("An error occurred while recovering address values.", e);
            }
        }
    }
}
