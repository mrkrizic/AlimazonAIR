package com.alimazon.air.controllers;

import com.alimazon.air.exception.RobotNotFoundException;
import com.alimazon.air.model.Robot;
import com.alimazon.air.respository.RobotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/air")
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

    @PutMapping("/robots{id}")
    public ResponseEntity<Robot> updateEmployee(@PathVariable(value = "id") Long robotId,
                                                @Valid @RequestBody Robot robotDetails) throws RobotNotFoundException {
        Robot robot = repository.findById(robotId)
                .orElseThrow(() -> new RobotNotFoundException("Robot with id " + robotId + " not found!"));
        robot.setId(robotDetails.getId()); //Redundant?
        robot.setLocation(robotDetails.getLocation());
        robot.setRobotType(robotDetails.getRobotType());
        robot.setWarehouseId(robotDetails.getWarehouseId());
        final Robot updatedRobot = repository.save(robot);
        return ResponseEntity.ok(updatedRobot);
    }

    @DeleteMapping("/robots/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long robotId) throws RobotNotFoundException {
        Robot robot = repository.findById(robotId)
                .orElseThrow(() -> new RobotNotFoundException("Robot with id " + robotId + " not found!"));
        repository.delete(robot);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
