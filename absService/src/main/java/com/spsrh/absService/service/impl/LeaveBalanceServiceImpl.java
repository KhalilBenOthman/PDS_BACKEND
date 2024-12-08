package com.spsrh.absService.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spsrh.absService.model.LeaveBalance;
import com.spsrh.absService.repository.LeaveBalanceRepository;
import com.spsrh.absService.service.LeaveBalanceService;

@Service
	public class LeaveBalanceServiceImpl implements LeaveBalanceService {

	    @Autowired
	    private LeaveBalanceRepository leaveBalanceRepository;

	    @Override
	    public LeaveBalance getLeaveBalance(Long employeeId) {
	        return leaveBalanceRepository.findByEmployeeId(employeeId)
	            .orElseThrow(() -> new RuntimeException("Leave balance not found for employee ID " + employeeId));
	    }

	    @Override
	    public void updateLeaveBalance(Long employeeId, int daysUsed) {
	        LeaveBalance leaveBalance = getLeaveBalance(employeeId);
	        leaveBalance.setUsedLeaves(leaveBalance.getUsedLeaves() + daysUsed);
	        leaveBalance.setRemainingLeaves(leaveBalance.getRemainingLeaves() - daysUsed);
	        leaveBalanceRepository.save(leaveBalance);
	    }
	}


