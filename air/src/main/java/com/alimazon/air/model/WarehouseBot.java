package com.alimazon.air.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="warehouse_bot")
public class WarehouseBot extends Robot{

    @Column(name = "capacity")
    private String maxLoad;

    public String getMaxLoad() {
        return maxLoad;
    }

    public void setMaxLoad(String maxLoad) {
        this.maxLoad = maxLoad;
    }
}
