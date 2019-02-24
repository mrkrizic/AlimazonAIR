package com.alimazon.air.model;

import com.alimazon.air.model.enums.RobotType;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Task class to define the Task, specify which RobotType is capable of performing the task
 * and stores the Timestamp upon creation of the Task to make scheduling easier.
 */

@Data
@NoArgsConstructor
public class Task {
    private String task;
    private RobotType robotType;
    private Long timestamp;

    /**
     * Definition of the Task. Creates a new Task that can be passed to a robot.
     * Currently the Task is described using String to make this class testable
     *
     * @param task      description of the Task
     * @param robotType type of robot capable performing the Task
     */
    public Task(String task, RobotType robotType) {
        this.task = task;
        this.robotType = robotType;
        this.timestamp = System.currentTimeMillis();
    }

}
