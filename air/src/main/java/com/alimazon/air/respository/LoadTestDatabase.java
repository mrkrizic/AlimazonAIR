package com.alimazon.air.respository;

import com.alimazon.air.model.*;
import com.alimazon.air.model.enums.DroneType;
import com.alimazon.air.model.enums.RobotType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadTestDatabase {

    @Bean
    public CommandLineRunner loadTestData(RobotRepository repository) {
        return args -> {
            log.info("Preloading: " + repository.save(new Drone("Cairo", "CAI01", RobotType.DRONE, DroneType.FIXED_WING_DRONE)));
            log.info("Preloading: " + repository.save(new Drone("Tokyo", "TYO33", RobotType.DRONE, DroneType.QUADCOPTER)));
            log.info("Preloading: " + repository.save(new Drone("Linz", "LNZ02", RobotType.DRONE, DroneType.QUADCOPTER)));
            log.info("Preloading: " + repository.save(new WarehouseBot("Delaware", "DEL16", RobotType.WAREHOUSE_BOT, 10.0)));
            log.info("Preloading: " + repository.save(new WarehouseBot("Linz", "LNZ01", RobotType.WAREHOUSE_BOT, 20.0)));
        };
    }
}
