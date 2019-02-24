package com.alimazon.air.model;

import com.alimazon.air.model.enums.RobotStatus;
import com.alimazon.air.model.enums.RobotType;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.persistence.*;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Objects;

/**
 * Abstract Robot superclass. Each new Robot Type should extend from this class
 * to ensure it is supported by the database.
 * <p>
 * Lombok annotations are used to avoid boilerplate code as getters/setters, toString, etc.
 * Each Robot has a unique id that gets automatically generated when it gets created.
 * The Location gives information about the whereabouts of the robot. Currently countries are
 * used, but geographical coordinates would be preferred in a real world scenario.
 * A Robot is assigned to a warehouse with a unique id and can be reassigned if necessary.
 * A Robot has a type. Types are necessary to track which tasks can be performed by which robot.
 * Types are defined in the RobotType enum which can be extended if necessary.
 * Robots need a status. For more information on statuses take a look at the RobotStatus enum.
 * Each Robot has a unique IP_Address that gets shared when the robot registers for this service.
 * With this address it is possible to communicate with a robot via REST calls in both directions.
 * Robots have a Flask Server running which can be called to perform operations.
 * A Robot has a maximum capacity and should not handle payloads over it.
 */
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "robot")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Robot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "location")
    private String location;

    @Column(name = "warehouse_id")
    private String warehouseId;

    @Column(name = "robot_type")
    private RobotType robotType;

    @Column(name = "status")
    private RobotStatus status;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "capacity")
    private Double capacity;

    /**
     * The Constructor used to create a new Robot
     * Each subclass should call this constructor to initialize the necessary values.
     *
     * @param location    the location of the Robot
     * @param warehouseId the warehouse id
     * @param robotType   the type of the robot
     * @param ipAddress   the ip address of the robot
     * @param capacity    capacity of the Robot in kg
     */
    Robot(String location, String warehouseId, RobotType robotType, String ipAddress, Double capacity) {
        this.location = location;
        this.warehouseId = warehouseId;
        this.robotType = robotType;
        this.status = RobotStatus.DISCONNECTED;
        this.ipAddress = ipAddress;
        this.capacity = capacity;
    }

    /**
     * Method to execute a Task
     * A HTTP request is made with the task to the Robots' API which
     * gets uploaded to the micro controller and starts executing the task.
     * TODO pass task in post request
     */
    public ResponseEntity<String> executeTask(Task task) throws SocketTimeoutException {
        final String robotIP = getIpAddress();
        final String uri = "http://" + robotIP + ":5000/get_task/" + task;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        return ResponseEntity.ok().body(result);
    }
}
