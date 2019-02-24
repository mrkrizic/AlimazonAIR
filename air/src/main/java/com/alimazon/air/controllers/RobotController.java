package com.alimazon.air.controllers;

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
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Implementation of the RobotController
 * It is built using a RestController and provides a CRUD interface for the Robots and
 * also to pass tasks to a robot, which get uploaded and executed.
 * The Tasks are currently only mock ups to test the functionality of this Controller
 * This controller uses the swagger framework for a clean, responsive user interface
 * To use swagger use the following url: http://localhost:8080/swagger-ui.html#
 * To use basic functionality use the following url: http://localhost:8080/api/air/
 * and add the mapping defined in the functions
 * Example: Enter "http://localhost:8080/api/air/robots" in the browser to view all robots
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
     * @throws RobotNotFoundException no robot found for given id
     */
    @GetMapping("/robots/{id}")
    public ResponseEntity<Robot> getRobotById(@PathVariable(value = "id") Long robotId) throws RobotNotFoundException {
        Robot robot = repository.findById(robotId)
                .orElseThrow(() -> new RobotNotFoundException("Robot with id " + robotId + " not found!"));
        return ResponseEntity.ok().body(robot);
    }

    /**
     * This adds the drone to the database and makes it usable
     * Every new drone should be registered using this method
     * TODO getters and setters for all entities
     *
     * @param drone the drone to be registered
     * @return new drone
     */
    @PostMapping("/create/drone")
    public Robot createDrone(Drone drone) {
        Robot robot = checkDuplicateIp(drone.getIpAddress());
        if (robot != null) return robot;
        drone.setRobotType(RobotType.DRONE);

        return repository.save(drone);
    }

    /**
     * This adds the warehouse bot to the database and makes it usable
     * Every new bot should be registered using this method
     *
     * @param bot the warehouse bot to be registered
     * @return new bot
     */
    @PostMapping("/create/warehouseBot")
    public Robot createWarehouseBot(WarehouseBot bot) {
        Robot robot = checkDuplicateIp(bot.getIpAddress());
        if (robot != null) return robot;
        bot.setRobotType(RobotType.WAREHOUSE_BOT);
        return repository.save(bot);
    }

    /**
     * private method that checks if a robot with this ip address has already been registered
     * If this robot already exists or has registered before but has disconnected,
     * its' status is set to idle
     *
     * @param ipAddress the ip address of the robot
     * @return robot with given ip address
     */
    private Robot checkDuplicateIp(String ipAddress) {
        Optional<Robot> robotWithIp = repository.findByIpAddress(ipAddress);
        if (robotWithIp.isPresent()) {
            Robot robot = robotWithIp.get();
            robot.setStatus(RobotStatus.IDLE);
            repository.save(robot);
            return robot;
        }
        return null;
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
     * @param statusString the status to be set
     * @return updated Robot
     */
    @PostMapping("/robots/status/{id}")
    public ResponseEntity<Robot> setRobotStatus(@PathVariable(value = "id") Long robotId,
                                                String statusString) {
        RobotStatus status = RobotStatus.fromString(statusString);
        Robot robot = repository.findById(robotId)
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
     * @throws RobotNotFoundException no robot found for given id
     */
    @PutMapping("/robots/{id}")
    public ResponseEntity<Robot> updateRobot(@PathVariable(value = "id") Long robotId,
                                             @RequestBody Robot robotDetails) throws RobotNotFoundException {
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
     * @throws RobotNotFoundException no robot found for given id
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

    /*^*****************************************************************************************************************
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


    /*^*****************************************************************************************************************
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

    /*^*****************************************************************************************************************
     * ======================================== Warehouse API ==========================================================
     * *****************************************************************************************************************/

    /**
     * Schedules a task to pass to a bot in the warehouse with given id
     *
     * If there is no available robot or all robots are busy, the task should be
     * put into a queue that passes the task to the first available robot via a REST call
     * Tasks have a timestamp and should be sorted accordingly (sooner to later)
     * The queue should be checked every time a robot switches to idle
     * TODO implement scheduling
     * @param warehouseId warehouse id
     * @return Task
     * @throws RobotNotFoundException no robot found for given id
     */
    @PostMapping("/warehouse/scheduleTask/{warehouse_id}")
    public ResponseEntity<String> scheduleTask(@PathVariable(value = "warehouse_id") String warehouseId, String taskInfo, String robotType)
            throws RobotNotFoundException {
        Task task = new Task(taskInfo, RobotType.fromString(robotType));
        List<Robot> robots = repository.findByWarehouseId(warehouseId);
        for (Robot robot : robots) {
            if (robot.getStatus().equals(RobotStatus.IDLE)
                    && robot.getRobotType().equals(task.getRobotType())) {
                try {
                    robot.setStatus(RobotStatus.BUSY);
                    repository.save(robot);

                    ResponseEntity<String> robotResponse = robot.executeTask(task);

                    robot.setStatus(RobotStatus.IDLE);
                    repository.save(robot);

                    return robotResponse;
                } catch (Exception cex) {
                    robot.setStatus(RobotStatus.DISCONNECTED);
                    repository.save(robot);
                    continue;
                }
            }
        }
        return ResponseEntity.ok().body("No free Robot available");
    }

    /**
     * Returns all Drones in the warehouse with the given id
     *
     * @param warehouseId warehouse id
     * @return list of drones at warehouse with given id
     * @throws RobotNotFoundException no robot found for given id
     */
    @GetMapping("/warehouse/getDronesAt/{id}")
    public List<Robot> getAllDrones(@PathVariable(value = "id") String warehouseId) throws RobotNotFoundException {
        return repository.findAll().stream()
                .filter(bot -> bot.getWarehouseId().equals(warehouseId))
                .collect(toList());
    }

    /*^*****************************************************************************************************************
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
