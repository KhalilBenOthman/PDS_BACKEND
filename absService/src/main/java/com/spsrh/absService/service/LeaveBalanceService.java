package com.spsrh.absService.service;

import java.util.List;

import com.spsrh.absService.dto.LeaveBalanceDTO;
import com.spsrh.absService.model.LeaveBalance;

public interface LeaveBalanceService {
    // Retrieve leave balance by employee username
    //LeaveBalanceDTO getLeaveBalanceByEmployeeUsername(String username);

    List<LeaveBalance> getLeaveBalanceListByEmployeeUsername(String username);
    // Retrieve all leave balances
    List<LeaveBalanceDTO> getAllLeaveBalances();

    // Update leave balance for an employee
    LeaveBalanceDTO updateLeaveBalance(String username, LeaveBalanceDTO leaveBalanceDTO);

    // Reset leave balance for all employees (e.g., at the end of the year)
    void resetAllLeaveBalances();

    LeaveBalance createLeaveBalance(LeaveBalanceDTO leaveBalanceDTO);
}

