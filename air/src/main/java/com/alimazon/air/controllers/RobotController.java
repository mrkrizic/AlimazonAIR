package com.alimazon.air.controllers;

import com.alimazon.air.error_handling.InvalidStatusException;
import com.alimazon.air.error_handling.RobotNotFoundException;
import com.alimazon.air.model.Drone;
import com.alimazon.air.model.Robot;
import com.alimazon.air.model.Task;
import com.alimazon.air.model.WarehouseBot;
import com.alimazon.air.model.enums.RobotStatus;
import com.alimazon.air.model.enums.RobotType;
import com.alimazon.air.respository.RobotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Implementation of the RobotController
 * It is built using a RestController and provides a CRUD interface for the Robots and
 * also to pass tasks to a robot, which get uploaded and executed.
 * The Tasks are currently only mock ups to test the functionality of this Controller
 * This controller uses the swagger framework for a clean, responsive user interface
 * To use swagger use the following url: http://localhost:8080/swagger-ui.html#
 */
@RestController
@RequestMapping("/api/air")
public class RobotController {

    /**
     * This is the connection to the Database
     */
    private final RobotRepository repository;

    /**
     * Constructor to auto wire the controller
     *
     * @param repository repository that stores the robots
     */
    @Autowired
    public RobotController(RobotRepository repository) {
        this.repository = repository;
    }

    /**
     * This methods returns all Robots regardless of their location
     *
     * @return all Robots
     */
    @GetMapping("/robots")
    public List<Robot> getAllRobots() {
        return repository.findAll();
    }

    /**
     * This gets the Robot with the requested id
     *
     * @param robotId the id of the robot
     * @return robot with this id
     * @throws RobotNotFoundException
     */
    @GetMapping("/robots/{id}")
    public ResponseEntity<Robot> getRobotById(@PathVariable(value = "id") Long robotId) throws RobotNotFoundException {
        Robot robot = repository.findById(robotId)
                .orElseThrow(() -> new RobotNotFoundException("Robot with id " + robotId + " not found!"));
        return ResponseEntity.ok().body(robot);
    }

    /**
     * This adds the robot to the database and makes it usable
     * Every new robot should be registered using this method
     *
     * @param robot the robot to be registered
     * @return new robot
     */
    @PostMapping("/robots")
    public Robot createRobot(@Valid @RequestBody Robot robot) {
        return repository.save(robot);
    }

    /**
     * Changes the status of the Robot.
     * Currently this method should be called by the Robot when:
     * Robot gets new Task - switch to BUSY
     * Robot finishes Task - switch to IDLE
     * Robot gets damaged - switch to DAMAGED
     * Robot cannot fix the Damage itself - switch to REQUEST_REPAIRS
     * Robot is not available anymore - switch to DISCONNECTED
     *
     * @param robotId      the id of the Robot
     * @param robotDetails the  details of the Robot
     * @param status       the status to be set
     * @return updated Robot
     */
    @PostMapping("/robots/status/idle/{id}")
    public ResponseEntity<Robot> setRobotStatus(@PathVariable(value = "id") Long robotId,
                                                @Valid @RequestBody Robot robotDetails,
                                                RobotStatus status) {
        if (!status.isValid()) {
            throw new InvalidStatusException(status + " is not a valid status!");
        }
        Robot robot = repository.findById(robotDetails.getId())
                .orElseThrow(() -> new RobotNotFoundException("Robot with id " + robotId + " not found!"));
        robot.setStatus(status);
        final Robot updatedRobot = repository.save(robot);
        return ResponseEntity.ok(updatedRobot);
    }

    /**
     * Updates the Robot infos
     * Use this if a robot gets assigned to a different warehouse, etc.
     *
     * @param robotId      the id of the robot
     * @param robotDetails the details of the robot
     * @return the updated robot
     * @throws RobotNotFoundException
     */
    @PutMapping("/robots/{id}")
    public ResponseEntity<Robot> updateRobot(@PathVariable(value = "id") Long robotId,
                                             @Valid @RequestBody Robot robotDetails) throws RobotNotFoundException {
        Robot robot = repository.findById(robotId)
                .orElseThrow(() -> new RobotNotFoundException("Robot with id " + robotId + " not found!"));
        robot.setLocation(robotDetails.getLocation());
        robot.setRobotType(robotDetails.getRobotType());
        robot.setWarehouseId(robotDetails.getWarehouseId());
        robot.setIpAddress(robotDetails.getIpAddress());
        final Robot updatedRobot = repository.save(robot);
        return ResponseEntity.ok(updatedRobot);
    }

    /**
     * If a robot gets discontinued or is damaged beyond repairs
     * it can get removed from the database using this method
     *
     * @param robotId the id of the robot
     * @return the deleted robot
     * @throws RobotNotFoundException
     */
    @DeleteMapping("/robots/{id}")
    public Map<String, Boolean> deleteRobot(@PathVariable(value = "id") Long robotId) throws RobotNotFoundException {
        Robot robot = repository.findById(robotId)
                .orElseThrow(() -> new RobotNotFoundException("Robot with id " + robotId + " not found!"));
        repository.delete(robot);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    /*******************************************************************************************************************
     * ==================================== Warehouse Bot API ==========================================================
     * *****************************************************************************************************************/
    /**
     * Returns all Warehouse bots
     *
     * @return warehouse bots
     */
    @GetMapping("/warehouse_bots")
    public List<Robot> getAllWarehouseBots() {
        return repository.findAll().stream()
                .filter(bot -> bot.getClass().getName().equals(WarehouseBot.class.getName()))
                .collect(toList());
    }


    /*******************************************************************************************************************
     * ======================================== Drone Bot API ==========================================================
     * *****************************************************************************************************************/
    /**
     * Returns a list of all drones
     *
     * @return drones
     */
    @GetMapping("/drones")
    public List<Robot> getAllDrones() {
        return repository.findAll().stream()
                .filter(bot -> bot.getClass().getName().equals(Drone.class.getName()))
                .collect(toList());
    }

    /*******************************************************************************************************************
     * ======================================== Warehouse API ==========================================================
     * *****************************************************************************************************************/

    /**
     * Schedules a task to pass to a bot in the warehouse with given id
     * If there is no available robot or all robots are busy, the task should be
     * put into a queue that passes the task to the first available robot via a REST call
     * Tasks have a timestamp and should be sorted accordingly (sooner to later)
     * The queue should be checked every time a robot switches to idle
     *
     * @param id robot id
     * @return Task
     * @throws RobotNotFoundException
     */
    @PostMapping("/warehouse/scheduleTask/{id}")
    public ResponseEntity<String> scheduleTask(@PathVariable(value = "id") String id, Task task)
            throws RobotNotFoundException {
        List<Robot> robots = repository.findAll().stream()
                .filter(bot -> bot.getWarehouseId().equals(id))
                .collect(toList());
        Robot freeRobot = null;
        for (Robot robot : robots) {
            if (robot.getStatus().equals(RobotStatus.IDLE)
                    && robot.getRobotType().equals(task.getRobotType())) {
                freeRobot = robot;
            }
        }
        if (freeRobot == null) {
            //TODO schedule first free robot for the Task
            return ResponseEntity.ok().body("No free Robot available");
        }
        //TODO switch to idle once finished
        return freeRobot.executeTask(task);
    }

    /**
     * Returns all Drones in the warehouse with the given id
     *
     * @param id warehouse id
     * @return list of drones at warehouse with given id
     * @throws RobotNotFoundException
     */
    @GetMapping("/warehouse/getDronesAt/{id}")
    public List<Robot> getAllDrones(@PathVariable(value = "id") String id) throws RobotNotFoundException {
        return repository.findAll().stream()
                .filter(bot -> bot.getWarehouseId().equals(id))
                .collect(toList());
    }

    /*******************************************************************************************************************
     * ======================================== Web Service API ========================================================
     * *****************************************************************************************************************/

    /**
     * This should be implemented in the robot if it needs more cpu power
     * Currently only mock up
     *
     * @return if connection was successful
     */
    @GetMapping("/web_service/connect")
    public ResponseEntity<Boolean> connectToWebService() {
        return ResponseEntity.ok().body(Boolean.TRUE);
    }

    /**
     * Disconnects robot from a webservice when finished using extra cpu power
     * Currently only mock up
     *
     * @return disconnected successful
     */
    @GetMapping("/web_service/disconnect")
    public ResponseEntity<Boolean> disconnectFromWebService() {
        return ResponseEntity.ok().body(Boolean.TRUE);
    }

}
