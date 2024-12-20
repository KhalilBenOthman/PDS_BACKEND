package com.spsrh.absService.mapper;

import com.spsrh.absService.dto.LeaveRequestDTO;
import com.spsrh.absService.model.LeaveRequest;
import org.springframework.stereotype.Component;

@Component
public class LeaveRequestMapper {

    // Convert entity to DTO
    public LeaveRequestDTO toDTO(LeaveRequest leaveRequest) {
        if (leaveRequest == null) {
            return null;
        }

        LeaveRequestDTO leaveRequestDTO = new LeaveRequestDTO();
        leaveRequestDTO.setId(leaveRequest.getId());
        leaveRequestDTO.setEmployeeUsername(leaveRequest.getEmployeeUsername());
        leaveRequestDTO.setManagerUsername(leaveRequest.getManagerUsername());
        leaveRequestDTO.setLeaveType(leaveRequest.getLeaveType());
        leaveRequestDTO.setStartDate(leaveRequest.getStartDate());
        leaveRequestDTO.setEndDate(leaveRequest.getEndDate());
        leaveRequestDTO.setLeaveStatus(leaveRequest.getLeaveStatus());
        return leaveRequestDTO;
    }

    // Convert DTO to entity
    public LeaveRequest toEntity(LeaveRequestDTO leaveRequestDTO) {
        if (leaveRequestDTO == null) {
            return null;
        }

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setId(leaveRequestDTO.getId());
        leaveRequest.setEmployeeUsername(leaveRequestDTO.getEmployeeUsername());
        leaveRequest.setManagerUsername(leaveRequestDTO.getManagerUsername());
        leaveRequest.setLeaveType(leaveRequestDTO.getLeaveType());
        leaveRequest.setStartDate(leaveRequestDTO.getStartDate());
        leaveRequest.setEndDate(leaveRequestDTO.getEndDate());
        leaveRequest.setLeaveStatus(leaveRequestDTO.getLeaveStatus());
        leaveRequest.setReason(leaveRequestDTO.getReason());
        leaveRequest.setRejectionReason(leaveRequestDTO.getRejectionReason());
        return leaveRequest;
    }
}
