package com.attus.attusbackendchallenge.infra.helpers;

import com.attus.attusbackendchallenge.model.*;
import com.attus.attusbackendchallenge.model.exceptions.MainAddressNotFoundException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

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

    public static MapSqlParameterSource namedParameters(AddressIdentifier id) {
        return new MapSqlParameterSource("id", Long.parseLong(id.value()));
    }

    public static MapSqlParameterSource namedParameters(PersonIdentifier personId) {
        return new MapSqlParameterSource("person_id", Long.parseLong(personId.value()));
    }

    public static class PersonAddressesRowMapper implements RowMapper<PersonAddresses> {
        @Override
        public PersonAddresses mapRow(ResultSet rs, int rowNum) throws SQLException {
            AddressRowMapper addressRowMapper = new AddressRowMapper();
            List<Address> addressList = new ArrayList<>();
            AddressIdentifier mainAddressId = null;
            do {
                addressList.add(addressRowMapper.mapRow(rs, rowNum));
                if (rs.getBoolean("is_main_address")) {
                    mainAddressId = DatabaseIdentifier.of(rs.getLong("id"));
                }
            } while (rs.next());

            if (isNull(mainAddressId)) {
                throw new MainAddressNotFoundException("There is no stored main address for Person");
            }

            return new PersonAddresses(addressList, mainAddressId);
        }
    }

    public static class AddressRowMapper implements RowMapper<Address> {

        @Override
        public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
            Address address = new Address(
                    new BrazilianCEP(rs.getString("postal_code")),
                    BrazilianStates.valueOf(rs.getString("state")),
                    new City(rs.getString("city")),
                    new ResidenceStreet(rs.getString("street")),
                    new ResidenceIdentifier(rs.getString("number"))
            );
            address.setIdentifier(DatabaseIdentifier.of(rs.getInt("id")));
            return address;
        }
    }
}
