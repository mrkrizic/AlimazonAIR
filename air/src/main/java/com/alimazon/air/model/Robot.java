package com.alimazon.air.model;

import javax.persistence.*;

@Entity
@Table(name = "robot")
@Inheritance(strategy = InheritanceType.JOINED)
public class Robot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "location")
    private String location;

    @Column(name = "warehouse_id")
    private String warehouseId;

    @Column(name = "robot_type")
    private String robotType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getRobotType() {
        return robotType;
    }

    public void setRobotType(String robotType) {
        this.robotType = robotType;
    }

    @Override
    public String toString() {
        return "Robot{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", warehouseId='" + warehouseId + '\'' +
                ", robotType='" + robotType + '\'' +
                '}';
    }
}
