package com.attus.attusbackendchallenge.fixtures;

import com.attus.attusbackendchallenge.model.BrazilianCEP;
import com.attus.attusbackendchallenge.model.PostalCodeFormat;

public class BrazilianCEPFixture {
    public static PostalCodeFormat aBrazilianCEP() {
        return new BrazilianCEP("05653-070");
    }

    public static PostalCodeFormat anotherBrazilianCEP() {
        return new BrazilianCEP("90250-590");
    }

    public static PostalCodeFormat aNewBrazilianCEP() {
        return new BrazilianCEP("60861-211");
    }

    public static PostalCodeFormat aSecondNewBrazilianCEP() {
        return new BrazilianCEP("20921-060");
    }
}
