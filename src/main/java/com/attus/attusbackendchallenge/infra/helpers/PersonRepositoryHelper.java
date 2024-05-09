package com.attus.attusbackendchallenge.infra.helpers;

import com.attus.attusbackendchallenge.model.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRepositoryHelper {
    public static MapSqlParameterSource params(Person person) {
        MapSqlParameterSource params = new MapSqlParameterSource("first_name", person.firstName().value());
        params.addValue("last_name", person.lastName().value());
        params.addValue("birth_date", person.birthDate().value());
        return params;
    }

    public static MapSqlParameterSource params(PersonIdentifier identifier, Person person) {
        MapSqlParameterSource params = new MapSqlParameterSource("first_name", person.firstName().value());
        params.addValue("last_name", person.lastName().value());
        params.addValue("birth_date", person.birthDate().value());
        params.addValue("id", identifier.value());
        return params;
    }

    public static MapSqlParameterSource params(PersonIdentifier identifier) {
        return new MapSqlParameterSource("id", identifier.value());
    }

    public static class PersonRowMapper implements RowMapper<Person> {

        @Override
        public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
            Person person = new Person(
                    new PersonName(rs.getString("first_name")),
                    new PersonName(rs.getString("last_name")),
                    new BirthDate(rs.getDate("birth_date"))
            );
            person.setIdentifier(DatabaseIdentifier.of(rs.getLong("id")));

            return person;
        }
    }
}
