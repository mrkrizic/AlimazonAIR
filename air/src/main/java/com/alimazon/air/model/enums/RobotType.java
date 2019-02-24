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
     * Generates a robot type from a input string
     *
     * @param fieldDescription field description (case insensitive)
     * @return robot type
     */
    public static RobotType fromString(String fieldDescription) {
        for (RobotType type : RobotType.values()) {
            if (type.fieldDescription.equalsIgnoreCase(fieldDescription)) {
                return type;
            }
        }
        return null;
    }

    /**
     * Returns string representation of a type
     *
     * @return field description
     */
    @Override
    public String toString() {
        return fieldDescription;
    }}
