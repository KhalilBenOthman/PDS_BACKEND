package com.spsrh.userService.controller;

import com.spsrh.userService.dto.EmployeeDTO;
import com.spsrh.userService.exception.EmployeNotFoundException;
import com.spsrh.userService.exception.InvalidAssignationException;
import com.spsrh.userService.service.EmployeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
public class EmployeController {

    private final EmployeService employeService;

    public EmployeController(EmployeService employeService) {
        this.employeService = employeService;
    }

    // Get an employee by username
    @GetMapping("/{username}")
    public ResponseEntity<EmployeeDTO> getEmploye(@PathVariable String username) {
        try {
            EmployeeDTO employe = employeService.getEmployeByUsername(username);
            return new ResponseEntity<>(employe, HttpStatus.OK);
        } catch (EmployeNotFoundException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Get all employees
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployes() {
        List<EmployeeDTO> employes = employeService.getAllEmployes();
        return new ResponseEntity<>(employes, HttpStatus.OK);
    }

    // Assign a manager to an employee
    @PutMapping("/{employeUsername}/assign-manager/{managerUsername}")
    public ResponseEntity<String> assignManagerToEmploye(
            @PathVariable String employeUsername,
            @PathVariable String managerUsername) {

        try {
            employeService.assignManager(employeUsername, managerUsername);
            return new ResponseEntity<>("Manager assigned successfully", HttpStatus.OK);
        } catch (InvalidAssignationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
