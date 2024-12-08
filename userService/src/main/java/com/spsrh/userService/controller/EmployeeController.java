package com.spsrh.userService.controller;

import com.spsrh.userService.dto.EmployeeDTO;
import com.spsrh.userService.model.Employee;
import com.spsrh.userService.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO, BindingResult result) {
        // Delegate to the service to create the employee
        return employeeService.createEmployee(employeeDTO, result);
    }

    @PutMapping("/update/{employeeId}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long employeeId, 
                                            @Valid @RequestBody EmployeeDTO employeeDTO, 
                                            BindingResult result) {
        // Delegate to the service to update the employee
        return employeeService.updateEmployee(employeeId, employeeDTO, result);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long employeeId) {
        // Retrieve the employee by ID
        return employeeService.getEmployeeById(employeeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        // Retrieve all employees
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long employeeId) {
        // Delegate to the service to delete the employee
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }
}
