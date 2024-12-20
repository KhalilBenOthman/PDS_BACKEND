package com.spsrh.userService.controller;

import com.spsrh.userService.dto.ManagerDTO;
import com.spsrh.userService.exception.ManagerNotFoundException;
import com.spsrh.userService.exception.InvalidAssignationException;
import com.spsrh.userService.service.ManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/managers")
public class ManagerController {

    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    // Get a manager by username
    @GetMapping("/{username}")
    public ResponseEntity<ManagerDTO> getManager(@PathVariable String username) {
        try {
            ManagerDTO manager = managerService.getManagerByUsername(username);
            return new ResponseEntity<>(manager, HttpStatus.OK);
        } catch (ManagerNotFoundException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Get all managers
    @GetMapping
    public ResponseEntity<List<ManagerDTO>> getAllManagers() {
        List<ManagerDTO> managers = managerService.getAllManagers();
        return new ResponseEntity<>(managers, HttpStatus.OK);
    }

    // Add an employee to a manager’s team
    @PutMapping("/{managerUsername}/add-employe/{employeUsername}")
    public ResponseEntity<String> addEmployeToManagerTeam(
            @PathVariable String managerUsername,
            @PathVariable String employeUsername) {

        try {
            managerService.addEmployeToTeam(managerUsername, employeUsername);
            return new ResponseEntity<>("Employee added to manager's team", HttpStatus.OK);
        } catch (InvalidAssignationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Remove an employee from a manager’s team
    @PutMapping("/{managerUsername}/remove-employe/{employeUsername}")
    public ResponseEntity<String> removeEmployeFromManagerTeam(
            @PathVariable String managerUsername,
            @PathVariable String employeUsername) {

        try {
            managerService.removeEmployeFromTeam(managerUsername, employeUsername);
            return new ResponseEntity<>("Employee removed from manager's team", HttpStatus.OK);
        } catch (InvalidAssignationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
