package com.alimazon.air.model;

import com.alimazon.air.model.enums.RobotType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Warehouse bot implementation. Extends the abstract Robot superclass.
 * Bots handle the transport of packages inside the warehouse.
 * For more information see the abstract Robot class
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "warehouse_bot")
public class WarehouseBot extends Robot {

    /**
     * Warehouse Bot constructor. This creates a new Warehouse bot that can be registered in
     * the database.
     *
     * @param location    location of the bot
     * @param warehouseId id of the warehouse
     * @param robotType   type of the bot
     * @param ipAddress   ip address of the bot
     * @param capacity    capacity of the bot
     */
    public WarehouseBot(String location, String warehouseId, RobotType robotType,
                        String ipAddress, Double capacity) {
        super(location, warehouseId, robotType, ipAddress, capacity);
    }

}
