package com.spsrh.userService.service;

import java.util.List;

import com.spsrh.userService.dto.ManagerDTO;
import com.spsrh.userService.model.Manager;

public interface ManagerService {
    Manager createManager(ManagerDTO managerDTO);
    List<Manager> getAllManagers();
    Manager getManagerById(Long id);
    Manager updateManager(Long id, ManagerDTO managerDTO);
    void deleteManager(Long id);
}