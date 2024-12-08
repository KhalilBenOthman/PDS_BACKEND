package com.spsrh.userService.service.impl;

import com.spsrh.userService.dto.EmployeeDTO;
import com.spsrh.userService.model.Department;
import com.spsrh.userService.model.Employee;
import com.spsrh.userService.model.ManagerTeam;
import com.spsrh.userService.model.User;
import com.spsrh.userService.repository.EmployeeRepository;
import com.spsrh.userService.repository.ManagerTeamRepository;
import com.spsrh.userService.repository.UserRepository;
import com.spsrh.userService.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private MapValidationErrorService mapValidationErrorService;
    @Autowired
    private ManagerTeamRepository managerTeamRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> createEmployee(EmployeeDTO employeeDTO, BindingResult result) {
        // Check for validation errors
        if (result.hasErrors()) {
            // Map and return validation errors as a ResponseEntity
            return ResponseEntity.badRequest().body(mapValidationErrorService.mapValidationError(result));
        }

        // Check if the user already exists, create it if not
        User user = userRepository.findById(employeeDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + employeeDTO.getUserId()));
        
        // Check if the manager exists, if provided
        User manager = null;
        if (employeeDTO.getManagerId() != null) {
            manager = userRepository.findById(employeeDTO.getManagerId())
                    .orElseThrow(() -> new IllegalArgumentException("Manager not found with ID: " + employeeDTO.getManagerId()));
        }

        // Create a new Employee instance and set its fields
        Employee newEmployee = new Employee();
        newEmployee.setUser(user);
        newEmployee.setManager(manager);
        newEmployee.setJobTitle(employeeDTO.getJobTitle());
        newEmployee.setDepartment(Department.valueOf(employeeDTO.getDepartment())); // Assuming department is an enum in DTO
        newEmployee.setSkills(employeeDTO.getSkills());
        newEmployee.setSalary(employeeDTO.getSalary());
        newEmployee.setAvailabilityStatus(employeeDTO.getAvailabilityStatus());
        newEmployee.setHireDate(employeeDTO.getHireDate());
        newEmployee.setLastPerformanceReviewDate(employeeDTO.getLastPerformanceReviewDate());

        // Save and return the created employee
        Employee savedEmployee  = employeeRepository.save(newEmployee);
        // Update the User to link the Employee
        user.setEmployee(savedEmployee);
        userRepository.save(user); // Persist the updated User entity
     // If the manager exists and the user is not an ADMIN, add the user to a ManagerTeam
        if (manager != null && !user.getRole().name().equalsIgnoreCase("ADMIN")) {
            ManagerTeam managerTeam = managerTeamRepository.findByManager(manager)
                    .orElse(null);

            if (managerTeam == null) {
                // Create a new ManagerTeam instance if it does not exist
                managerTeam = new ManagerTeam();
                managerTeam.setManager(manager);
                managerTeam = managerTeamRepository.save(managerTeam); // Save it to persist in the database
            }

            // Add the user to the manager's team and save
            if (!managerTeam.getEmployees().contains(user)) {
                managerTeam.getEmployees().add(user);
                managerTeamRepository.save(managerTeam);
            }

            // Update the user to set the team ID and save
            user.setTeam(managerTeam);
            userRepository.save(user);
        }
        
        
        
        
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }



    @Override
    public ResponseEntity<?> updateEmployee(Long employeeId, EmployeeDTO employeeDTO, BindingResult result) {
        // Check for validation errors
        if (result.hasErrors()) {
            // Map and return validation errors as a ResponseEntity
            return ResponseEntity.badRequest().body(mapValidationErrorService.mapValidationError(result));
        }

        // Find the existing employee
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + employeeId));

        // Check if the user exists
        User user = userRepository.findById(employeeDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + employeeDTO.getUserId()));

        // Check if the new manager exists, if provided
        User newManager = null;
        if (employeeDTO.getManagerId() != null) {
            newManager = userRepository.findById(employeeDTO.getManagerId())
                    .orElseThrow(() -> new IllegalArgumentException("Manager not found with ID: " + employeeDTO.getManagerId()));
        }

        // Check if the manager has changed
        User currentManager = existingEmployee.getManager();
        if (currentManager != null && !currentManager.equals(newManager)) {
            // Remove the employee from the previous manager's team
            ManagerTeam currentManagerTeam = managerTeamRepository.findByManager(currentManager).orElse(null);
            if (currentManagerTeam != null) {
                currentManagerTeam.getEmployees().remove(user);
                managerTeamRepository.save(currentManagerTeam);
            }
        }

        // Update the employee fields
        existingEmployee.setUser(user);
        existingEmployee.setManager(newManager);
        existingEmployee.setJobTitle(employeeDTO.getJobTitle());
        existingEmployee.setDepartment(Department.valueOf(employeeDTO.getDepartment())); // Assuming department is an enum in DTO
        existingEmployee.setSkills(employeeDTO.getSkills());
        existingEmployee.setSalary(employeeDTO.getSalary());
        existingEmployee.setAvailabilityStatus(employeeDTO.getAvailabilityStatus());
        existingEmployee.setHireDate(employeeDTO.getHireDate());
        existingEmployee.setLastPerformanceReviewDate(employeeDTO.getLastPerformanceReviewDate());

        // Save the updated employee
        Employee updatedEmployee = employeeRepository.save(existingEmployee);

        // Add the employee to the new manager's team if the manager is not null and the user is not an ADMIN
        if (newManager != null && !user.getRole().name().equalsIgnoreCase("ADMIN")) {
            ManagerTeam newManagerTeam = managerTeamRepository.findByManager(newManager).orElse(null);

            if (newManagerTeam == null) {
                // Create a new ManagerTeam instance if it does not exist
                newManagerTeam = new ManagerTeam();
                newManagerTeam.setManager(newManager);
                newManagerTeam = managerTeamRepository.save(newManagerTeam); // Save it to persist in the database
            }

            // Add the user to the new manager's team and save
            if (!newManagerTeam.getEmployees().contains(user)) {
                newManagerTeam.getEmployees().add(user);
                managerTeamRepository.save(newManagerTeam);
            }

            // Update the user to set the new team and save
            user.setTeam(newManagerTeam);
            userRepository.save(user);
        } else if (newManager == null) {
            // If no new manager is provided, remove the team reference for the user
            user.setTeam(null);
            userRepository.save(user);
        }

        return ResponseEntity.status(HttpStatus.OK).body(updatedEmployee);
    }



    @Override
    public Optional<Employee> getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

}
