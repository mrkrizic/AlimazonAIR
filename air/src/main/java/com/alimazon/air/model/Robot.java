package com.alimazon.air.model;

import com.alimazon.air.model.enums.RobotStatus;
import com.alimazon.air.model.enums.RobotType;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "robot")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Robot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "location")
    private String location;

    @Column(name = "warehouse_id")
    private String warehouseId;

    @Column(name = "robot_type")
    private RobotType robotType;

    @Column(name = "status")
    private RobotStatus status;

    @Column(name = "ip_addess")
    private String ipAddress;

    Robot() {
    }

    Robot(String location, String warehouseId, RobotType robotType, String ipAddress) {
        this.location = location;
        this.warehouseId = warehouseId;
        this.robotType = robotType;
        this.status = RobotStatus.DISCONNECTED;
        this.ipAddress = ipAddress;
    }

    public Long getId() {
        return id;
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

    public RobotType getRobotType() {
        return robotType;
    }

    public void setRobotType(RobotType robotType) {
        this.robotType = robotType;
    }

    public RobotStatus getStatus() {
        return status;
    }

    public void setStatus(RobotStatus status) {
        this.status = status;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void executeTask() {
        //TODO execute Task
    }

    @Override
    public String toString() {
        return "Robot{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", warehouseId='" + warehouseId + '\'' +
                ", robotType=" + robotType +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Robot)) return false;
        Robot robot = (Robot) o;
        return Objects.equals(getId(), robot.getId()) &&
                Objects.equals(getLocation(), robot.getLocation()) &&
                Objects.equals(getWarehouseId(), robot.getWarehouseId()) &&
                getRobotType() == robot.getRobotType() &&
                getStatus() == robot.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLocation(), getWarehouseId(), getRobotType(), getStatus());
    }
}
