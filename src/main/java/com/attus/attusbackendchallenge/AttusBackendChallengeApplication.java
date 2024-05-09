package com.attus.attusbackendchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class AttusBackendChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttusBackendChallengeApplication.class, args);
    }

}
