package com.attus.attusbackendchallenge.fixtures;

import com.attus.attusbackendchallenge.model.BirthDate;

import java.util.Date;

public class BirthDateFixture {
    public static BirthDate aBirthDate() {
        Date may2nd1996 = new Date(831006000000L);
        return new BirthDate(may2nd1996);
    }

    public static BirthDate anotherBirthDate() {
        Date june3rd1992 = new Date(707540400000L);
        return new BirthDate(june3rd1992);
    }

    public static BirthDate aNewBirthDate() {
        Date may311989 = new Date(612586800000L);
        return new BirthDate(may311989);
    }
}
