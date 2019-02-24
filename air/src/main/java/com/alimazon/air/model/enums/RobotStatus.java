package com.alimazon.air.model.enums;

import com.alimazon.air.model.Robot;

/**
 * Enum for each possible robot status
 * Robot goes offline - DISCONNECTED
 * Robot is online and has no current task - IDLE
 * Robot is executing a task - BUSY
 * Robot is not functioning and tries to repair itself (reboot for example) - DAMAGED
 * Robot cannot fix it's problem automatically - REQUESTING_REPAIRS
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
     * Creates a robot status from a string
     * @param statusString status string (case insensitive)
     * @return robot status
     */
    public static RobotStatus fromString(String statusString) {
        for (RobotStatus status : RobotStatus.values()) {
            if (status.fieldDescription.equalsIgnoreCase(statusString)) {
                return status;
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
