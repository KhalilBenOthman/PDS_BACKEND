package com.spsrh.userService.service;

import com.spsrh.userService.dto.EmployeeDTO;

import java.util.List;

public interface EmployeService {
    EmployeeDTO getEmployeByUsername(String username);
    List<EmployeeDTO> getAllEmployes();
    void assignManager(String employeUsername, String managerUsername);
}
