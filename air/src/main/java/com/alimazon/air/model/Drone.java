package com.alimazon.air.model;

import com.alimazon.air.model.enums.DroneType;
import com.alimazon.air.model.enums.RobotType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "drone")
public class Drone extends Robot {

    @Column(name = "drone_type")
    private DroneType droneType;


    private Drone() {
        super();
    }

    public Drone(String location, String warehouseId, RobotType robotType, DroneType droneType) {
        super(location, warehouseId, robotType);
        this.droneType = droneType;
    }

    public DroneType getDrone_type() {
        return droneType;
    }

    public void setDrone_type(DroneType drone_type) {
        this.droneType = drone_type;
    }

    @Override
    public String toString() {
        return "Drone{" + super.toString() +
                "droneType=" + droneType.toString() +
                '}';
    }
}
