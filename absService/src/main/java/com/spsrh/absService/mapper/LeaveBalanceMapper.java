package com.spsrh.absService.mapper;

import com.spsrh.absService.dto.LeaveBalanceDTO;
import com.spsrh.absService.model.LeaveBalance;
import org.springframework.stereotype.Component;

@Component
public class LeaveBalanceMapper {

    // Convert LeaveBalance entity to LeaveBalanceDTO
    public LeaveBalanceDTO toDTO(LeaveBalance leaveBalance) {
        if (leaveBalance == null) {
            return null;
        }

        LeaveBalanceDTO dto = new LeaveBalanceDTO();
        dto.setId(leaveBalance.getId());
        dto.setEmployeeUsername(leaveBalance.getEmployeeUsername());
        dto.setLeaveType(leaveBalance.getLeaveType());
        dto.setTotalDays(leaveBalance.getTotalDays());
        dto.setRemainingDays(leaveBalance.getRemainingDays());
        return dto;
    }

    // Convert LeaveBalanceDTO to LeaveBalance entity
    public LeaveBalance toEntity(LeaveBalanceDTO leaveBalanceDTO) {
        if (leaveBalanceDTO == null) {
            return null;
        }

        LeaveBalance leaveBalance = new LeaveBalance();
        leaveBalance.setId(leaveBalanceDTO.getId());
        leaveBalance.setEmployeeUsername(leaveBalanceDTO.getEmployeeUsername());
        leaveBalance.setLeaveType(leaveBalanceDTO.getLeaveType());
        leaveBalance.setTotalDays(leaveBalanceDTO.getTotalDays());
        leaveBalance.setRemainingDays(leaveBalanceDTO.getRemainingDays());
        return leaveBalance;
    }
}
