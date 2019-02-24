package com.alimazon.air;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application. It instantiates the Server, Database and loads Test Data if DEBUG is true.
 * If DEBUG is set to true, test data gets preloaded into the database. Only use for testing!
 */
@SpringBootApplication
public class AlimazonAirApplication {

    public static boolean DEBUG = false;

    public static void main(String[] args) {
        SpringApplication.run(AlimazonAirApplication.class, args);
    }

}

