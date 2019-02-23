package com.alimazon.air.model;

import com.alimazon.air.model.enums.RobotType;

import java.util.Objects;

/**
 * Task class to define the Task, specify which RobotType is capable of performing the task
 * and stores the Timestamp upon creation of the Task to make scheduling easier.
 */
public class Task {
    private String task;
    private RobotType robotType;
    private Long timestamp;

    /**
     * Necessary empty Constructor. Should not be used anywhere in the Program.
     */
    private Task() {
        task = "";
    }

    /**
     * Definition of the Task. Creates a new Task that can be passed to a robot.
     * Currently the Task is described using String to make this class testable
     * @param task description of the Task
     * @param robotType type of robot capable performing the Task
     */
    public Task(String task, RobotType robotType) {
        this.task = task;
        this.robotType = robotType;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     *
     * ============================== Getters ==============================
     */
    public String getTask() {
        return task;
    }

    public RobotType getRobotType() {
        return robotType;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    /**
     * ============================== toString, equals, hashcode ==============================
     */
    @Override
    public String toString() {
        return "Task{" +
                "task='" + task + '\'' +
                ", robotType=" + robotType +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task1 = (Task) o;
        return Objects.equals(getTask(), task1.getTask()) &&
                getRobotType() == task1.getRobotType() &&
                Objects.equals(getTimestamp(), task1.getTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTask(), getRobotType(), getTimestamp());
    }
}
