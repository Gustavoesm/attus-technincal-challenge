package com.attus.attusbackendchallenge.model;

public interface PersonIdentifier {
    String value();

    boolean equals(Object o);

    int hashCode();
}
