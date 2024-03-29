package com.alimazon.air.respository;

import com.alimazon.air.AlimazonAirApplication;
import com.alimazon.air.model.*;
import com.alimazon.air.model.enums.DroneType;
import com.alimazon.air.model.enums.RobotType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Loads dummy data into the Database. Only use during debugging and testing!
 */
@Configuration
@Slf4j
public class LoadTestDatabase {

    @Bean
    public CommandLineRunner loadTestData(RobotRepository repository) {
        return args -> {
            if (AlimazonAirApplication.DEBUG) {
                log.info("Preloading: " + repository.save(new Drone("Cairo", "CAI01", RobotType.DRONE, "192.168.98.2", DroneType.FIXED_WING_DRONE, 5.0)));
                log.info("Preloading: " + repository.save(new Drone("Tokyo", "TYO33", RobotType.DRONE, "192.168.53.44", DroneType.QUADCOPTER, 2.5)));
                log.info("Preloading: " + repository.save(new Drone("Linz", "LNZ02", RobotType.DRONE, "192.168.22.1", DroneType.QUADCOPTER, 2.0)));
                log.info("Preloading: " + repository.save(new WarehouseBot("Delaware", "DEL16", RobotType.WAREHOUSE_BOT, "192.168.1.125", 10.0)));
                log.info("Preloading: " + repository.save(new WarehouseBot("Linz", "LNZ01", RobotType.WAREHOUSE_BOT, "192.168.0.34", 20.0)));

            }
        };
    }
}
