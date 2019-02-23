package com.alimazon.air.model;

import com.alimazon.air.model.enums.RobotType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Warehouse bot implementation. Extends the abstract Robot superclass.
 * Bots handle the transport of packages inside the warehouse.
 * For more information see the abstract Robot class
 */
@Entity
@Table(name = "warehouse_bot")
public class WarehouseBot extends Robot {

    /**
     * Necessary Empty Constructor. Should not be used anywhere in the program.
     */
    private WarehouseBot() {
        super();
    }

    /**
     * Warehouse Bot constructor. This creates a new Warehouse bot that can be registered in
     * the database.
     * @param location location of the bot
     * @param warehouseId id of the warehouse
     * @param robotType type of the bot
     * @param ipAddress ip address of the bot
     * @param capacity capacity of the bot
     */
    public WarehouseBot(String location, String warehouseId, RobotType robotType,
                        String ipAddress, Double capacity) {
        super(location, warehouseId, robotType, ipAddress, capacity);
    }

    /**
     * ============================== toString, equals, hashcode ==============================
     */
    @Override
    public String toString() {
        return "WarehouseBot{" + super.toString() + '}';
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
