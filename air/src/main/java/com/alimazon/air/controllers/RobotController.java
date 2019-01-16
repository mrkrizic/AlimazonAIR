package com.alimazon.air.controllers;

import com.alimazon.air.error_handling.RobotNotFoundException;
import com.alimazon.air.model.Drone;
import com.alimazon.air.model.Robot;
import com.alimazon.air.model.WarehouseBot;
import com.alimazon.air.model.enums.RobotType;
import com.alimazon.air.respository.RobotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/air")
public class RobotController {

    @Autowired
    private RobotRepository repository;

    @GetMapping("/robots")
    public List<Robot> getAllRobots() {
        return repository.findAll();
    }


    @GetMapping("/robots/{id}")
    public ResponseEntity<Robot> getRobotById(@PathVariable(value = "id") Long robotId) throws RobotNotFoundException {
        Robot robot = repository.findById(robotId)
                .orElseThrow(() -> new RobotNotFoundException("Robot with id " + robotId + " not found!"));
        return ResponseEntity.ok().body(robot);
    }

    @PostMapping("/robots")
    public Robot createRobot(@Valid @RequestBody Robot robot) {
        return repository.save(robot);
    }

    @PutMapping("/robots/{id}")
    public ResponseEntity<Robot> updateRobot(@PathVariable(value = "id") Long robotId,
                                             @Valid @RequestBody Robot robotDetails) throws RobotNotFoundException {
        Robot robot = repository.findById(robotId)
                .orElseThrow(() -> new RobotNotFoundException("Robot with id " + robotId + " not found!"));
        robot.setLocation(robotDetails.getLocation());
        robot.setRobotType(robotDetails.getRobotType());
        robot.setWarehouseId(robotDetails.getWarehouseId());
        final Robot updatedRobot = repository.save(robot);
        return ResponseEntity.ok(updatedRobot);
    }

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
    @GetMapping("/warehouse_bots")
    public List<Robot> getAllWarehouseBots() {
        return repository.findAll().stream()
                .filter(bot -> bot.getClass().getName().equals(WarehouseBot.class.getName()))
                .collect(toList());
    }


    /*******************************************************************************************************************
     * ======================================== Drone Bot API ==========================================================
     * *****************************************************************************************************************/
    @GetMapping("/drones")
    public List<Robot> getAllDrones() {
        return repository.findAll().stream()
                .filter(bot -> bot.getClass().getName().equals(Drone.class.getName()))
                .collect(toList());
    }

    /*******************************************************************************************************************
     * ======================================== Warehouse API ==========================================================
     * *****************************************************************************************************************/

    @PostMapping("/warehouse/scheduleTask/{id}")
    public ResponseEntity<Robot> scheduleTask(@PathVariable(value = "id") String warehouseId, @Valid @RequestBody String task)
            throws RobotNotFoundException {
        //TODO schedule first free Robot for the Task in this Warehouse
        //Task should be a seperate class
        return ResponseEntity.ok().body(new WarehouseBot("", warehouseId, RobotType.WAREHOUSE_BOT, "0.0.0.0", 0.0));
    }

    @GetMapping("/warehouse/getDronesAt/{id}")
    public List<Robot> getAllDrones(@PathVariable(value = "id") String id) throws RobotNotFoundException {
        return repository.findAll().stream()
                .filter(bot -> bot.getWarehouseId().equals(id))
                .collect(toList());
    }

    /*******************************************************************************************************************
     * ======================================== Web Service API ========================================================
     * *****************************************************************************************************************/

    @GetMapping("/web_service/connect")
    public ResponseEntity<Boolean> connectToWebService() {
        return ResponseEntity.ok().body(Boolean.TRUE);
    }

    @GetMapping("/web_service/disconnect")
    public ResponseEntity<Boolean> disconnectFromWebService() {
        return ResponseEntity.ok().body(Boolean.TRUE);
    }

}
