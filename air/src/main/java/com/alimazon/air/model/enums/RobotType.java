package com.alimazon.air.model.enums;

/**
 * Enum for all supported Robot types
 * Used to determine capability of performing a task
 */
public enum RobotType {

    WAREHOUSE_BOT("Warehouse Bot"), DRONE("Drone");

    final String fieldDescription;

    /**
     * Constructor to assign string values to types
     *
     * @param value string value
     */
    RobotType(String value) {
        fieldDescription = value;
    }

    /**
     * Returns string representation of a type
     * @return field description
     */
    @Override
    public String toString() {
        return fieldDescription;
    }}
