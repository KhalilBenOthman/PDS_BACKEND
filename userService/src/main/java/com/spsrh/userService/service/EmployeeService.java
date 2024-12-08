package com.spsrh.userService.service;

import com.spsrh.userService.dto.EmployeeDTO;
import com.spsrh.userService.model.Employee;
import com.spsrh.userService.model.User;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface EmployeeService {
	 ResponseEntity<?> createEmployee(EmployeeDTO employeeDTO ,BindingResult result);
	 ResponseEntity<?> updateEmployee(Long employeeId, EmployeeDTO employeeDTO ,BindingResult result);
	 Optional<Employee> getEmployeeById(Long employeeId);
	 List<Employee> getAllEmployees();
	 void deleteEmployee(Long employeeId);
}
