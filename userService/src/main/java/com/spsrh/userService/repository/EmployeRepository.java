package com.spsrh.userService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spsrh.userService.model.Employee;

public interface EmployeRepository extends JpaRepository<Employee, Long> {

	Employee findByUsername(String employeUsername);
}
