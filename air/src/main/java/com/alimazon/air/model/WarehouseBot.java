package com.alimazon.air.model;

import com.alimazon.air.model.enums.RobotType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "warehouse_bot")
public class WarehouseBot extends Robot {

    @Column(name = "capacity")
    private Double maxLoad;

    private WarehouseBot() {
        super();
    }

    public WarehouseBot(String location, String warehouseId, RobotType robotType,String ipAddress, Double maxLoad) {
        super(location, warehouseId, robotType, ipAddress);
        this.maxLoad = maxLoad;
    }

    public void setMaxLoad(Double maxLoad) {
        this.maxLoad = maxLoad;
    }

    public Double getMaxLoad() {
        return maxLoad;
    }

    @Override
    public String toString() {
        return "WarehouseBot{" + super.toString() +
                "maxLoad=" + maxLoad +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WarehouseBot)) return false;
        if (!super.equals(o)) return false;
        WarehouseBot that = (WarehouseBot) o;
        return Objects.equals(getMaxLoad(), that.getMaxLoad());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getMaxLoad());
    }
}
