package com.spsrh.userService.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ManagerTeamDTO {
    private Long teamId;
    private UserDTO manager;
    private UserDTO employee;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<Long> employeeIds;
    
    // Getters and setters
    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public UserDTO getManager() {
        return manager;
    }

    public void setManager(UserDTO manager) {
        this.manager = manager;
    }

    public UserDTO getEmployee() {
        return employee;
    }

    public void setEmployee(UserDTO employee) {
        this.employee = employee;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public List<Long> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Long> employeeIds) {
        this.employeeIds = employeeIds;
    }
}
