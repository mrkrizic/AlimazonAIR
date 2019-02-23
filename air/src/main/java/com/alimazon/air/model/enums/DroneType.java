package com.alimazon.air.model.enums;

/**
 * Enum to differentiate between Fixed Wing Drones and Quadcopters
 * More Types can be added in the Future
 * Whenever a new type is introduced, it should be added here
 * Currently Fixed Wing drones and Quadcopters are supported.
 * Fixed Wing Drones can perform delivery in rural areas
 * Quadcopters should be used in densely populated cities
 * For more information on the types of Drones see: https://www.rand.org/pubs/research_reports/RR1718z2.html
 * The concrete implementation should be on the Drone itself
 */
public enum DroneType {

    FIXED_WING_DRONE("Fixed Wing Drone"), QUADCOPTER("Quadcopter");

    final String fieldDescription;

    /**
     * Constructor to assign string values to types
     *
     * @param value string value
     */
    DroneType(String value) {
        fieldDescription = value;
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
