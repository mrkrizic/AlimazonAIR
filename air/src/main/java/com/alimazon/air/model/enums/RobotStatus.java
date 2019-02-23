package com.alimazon.air.model.enums;

/**
 * Enum for each possible robot status
 * When the robot goes offline - DISCONNECTED
 * When the robot is online and has no current task - IDLE
 * When the robot is executing a task - BUSY
 * When the robot is not functioning and tries to repair itself (reboot for example) - DAMAGED
 * When the robot cannot fix it's problem automatically - REQUESTING_REPAIRS
 * If a status is passed it has to be checked if it is valid using the isValid() method
 */
public enum RobotStatus {

    DISCONNECTED("Disconnected"), IDLE("Idle"), BUSY("Busy"),
    DAMAGED("Damaged"), REQUESTING_REPAIRS("Request Repairs");

    final String fieldDescription;

    /**
     * Constructor to assign string value to states
     *
     * @param value string value
     */
    RobotStatus(String value) {
        fieldDescription = value;
    }

    /**
     * checks if the Status is valid
     *
     * @return validity of the status
     */
    public boolean isValid() {
        return this.equals(DISCONNECTED) ||
                this.equals(IDLE) ||
                this.equals(BUSY) ||
                this.equals(DAMAGED) ||
                this.equals(REQUESTING_REPAIRS);
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
