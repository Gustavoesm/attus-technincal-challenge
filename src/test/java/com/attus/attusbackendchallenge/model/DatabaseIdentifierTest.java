package com.attus.attusbackendchallenge.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class DatabaseIdentifierTest {

    @Test
    void verifyEqualsContract() {
        EqualsVerifier.simple().forClass(DatabaseIdentifier.class).verify();
    }
}