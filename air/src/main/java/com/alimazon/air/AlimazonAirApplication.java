package com.alimazon.air;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application. It instantiates the Server, Database, Test Data if necessary etc.
 */
@SpringBootApplication
public class AlimazonAirApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlimazonAirApplication.class, args);
    }

}

