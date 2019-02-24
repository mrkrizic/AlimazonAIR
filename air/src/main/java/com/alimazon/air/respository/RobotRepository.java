package com.alimazon.air.respository;

import com.alimazon.air.model.Robot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository where Robots can be stored. Stores a robot with its' id into the Database.
 * If another database is used, verify it can use this interface.
 * If the Database supports this interface this should not be changed.
 * Databases might need a different interface. In this case exclude this file and create a
 * compatible interface.
 */
@Repository
public interface RobotRepository extends JpaRepository<Robot, Long> {

    public List<Robot> findByWarehouseId(String warehouseId);

    Optional<Robot> findByIpAddress(String ipAddress);
}


