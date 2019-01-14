package com.alimazon.air.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "drone")
public class Drone extends Robot {

    @Column(name = "drone_type")
    private String drone_type;

    public String getDrone_type() {
        return drone_type;
    }

    public void setDrone_type(String drone_type) {
        this.drone_type = drone_type;
    }
}
