package com.spsrh.userService.service;

import com.spsrh.userService.dto.ManagerDTO;

import java.util.List;

public interface ManagerService {
    ManagerDTO getManagerByUsername(String username);
    List<ManagerDTO> getAllManagers();
    void addEmployeToTeam(String managerUsername, String employeUsername);
    void removeEmployeFromTeam(String managerUsername, String employeUsername);
}
