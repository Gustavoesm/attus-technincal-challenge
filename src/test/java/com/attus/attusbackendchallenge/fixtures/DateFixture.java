package com.attus.attusbackendchallenge.fixtures;

import java.util.Date;

public class DateFixture {

    public DateFixture() {
        // empty
    }

    public static Date dateOfMay27th1997() {
        return new Date(864702000000L);
    }

    public static Date dateOfJanuary1st1900() {
        return new Date(-2208977612000L);
    }

    public static Date dateOfOctober12th1950() {
        return new Date(-606603600000L);
    }

    public static Date dateOfDecember31th2000() {
        return new Date(978228000000L);
    }

    public static Date dateOfToday() {
        return new Date();
    }

    public static Date dateOfDecember31th1899() {
        return new Date(-2208977613000L);
    }

    public static Date dateOfTomorrow() {
        long ONE_DAY = 1000 * 60 * 60 * 24;
        return new Date(new Date().getTime() + ONE_DAY);
    }
}