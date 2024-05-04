package com.attus.attusbackendchallenge.model;

/**
 * This is an interface because we could want to enable international addresses in the future.
 * Use BrazilianStates Enum to insert/retrieve data for this field.
 */
public interface State {
    String longName();
    String shortName();
}
