package com.spsrh.userService.repository;

import com.spsrh.userService.model.Department;
import com.spsrh.userService.model.Employee;
import com.spsrh.userService.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByManager(User manager);
    List<Employee> findByDepartment(Department department);
    List<Employee> findByAvailabilityStatus(String availabilityStatus);
   
    Optional<Employee> findByUser_UserId(Long userId);
    Optional<Employee> findByUser_Username(String username);
    Optional<Employee> findByManager_UserId(Long managerId);

}
