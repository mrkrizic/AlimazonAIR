package com.alimazon.air.model;

import com.alimazon.air.model.enums.DroneType;
import com.alimazon.air.model.enums.RobotType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Drone implementation. Drones extend the functionality of a Robot and have a drone type.
 * Drones should be responsible for delivering items up to a maximum payload defined in capacity
 * For more information see the abstract Robot class.
 */

@Entity
@Table(name = "drone")
public class Drone extends Robot {

    /**
     * Adds a column with the drone type when storing into the database
     */
    @Column(name = "drone_type")
    private DroneType droneType;

    /**
     * Necessary empty constructor. Should not be used in the program
     */
    private Drone() {
        super();
    }

    /**
     * Drone Constructor. This should be used to create and register a new Drone.
     * @param location location of the drone
     * @param warehouseId id of the warehouse
     * @param robotType type of the robot
     * @param ipAddress ip address of the robot
     * @param droneType type of the drone
     * @param capacity maximum capacity of the drone
     */
    public Drone(String location, String warehouseId, RobotType robotType, String ipAddress,
                 DroneType droneType, Double capacity) {
        super(location, warehouseId, robotType, ipAddress, capacity);
        this.droneType = droneType;
    }

    /**
     * ============================== Getters and Setters ==============================
     */
    public DroneType getDrone_type() {
        return droneType;
    }

    public void setDrone_type(DroneType drone_type) {
        this.droneType = drone_type;
    }

    /**
     * ============================== toString, equals, hashcode ==============================
     */
    @Override
    public String toString() {
        return "Drone{" + super.toString() +
                "droneType=" + droneType.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Drone)) return false;
        if (!super.equals(o)) return false;
        Drone drone = (Drone) o;
        return droneType == drone.droneType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), droneType);
    }
}
