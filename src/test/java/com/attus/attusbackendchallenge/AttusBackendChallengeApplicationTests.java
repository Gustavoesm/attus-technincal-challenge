package com.attus.attusbackendchallenge;

import com.attus.attusbackendchallenge.controller.AddressController;
import com.attus.attusbackendchallenge.controller.PersonController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AttusBackendChallengeApplicationTests {

    @Autowired
    private PersonController personController;

    @Autowired
    private AddressController addressController;

    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {
        assertNotNull(context);
        assertNotNull(personController);
        assertNotNull(addressController);
    }
}
