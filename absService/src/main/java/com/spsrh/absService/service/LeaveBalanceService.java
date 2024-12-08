package com.spsrh.absService.service;

import com.spsrh.absService.model.LeaveBalance;

public interface LeaveBalanceService {
    LeaveBalance getLeaveBalance(Long employeeId);
    void updateLeaveBalance(Long employeeId, int daysUsed);
}

