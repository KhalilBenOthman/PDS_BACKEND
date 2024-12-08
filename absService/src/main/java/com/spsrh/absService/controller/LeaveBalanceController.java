package com.spsrh.absService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spsrh.absService.model.LeaveBalance;
import com.spsrh.absService.service.LeaveBalanceService;

@RestController
	@RequestMapping("/api/leave-balance")
	public class LeaveBalanceController {

	    @Autowired
	    private LeaveBalanceService leaveBalanceService;

	    // 1. Récupérer le solde de congés d'un employé
	    @GetMapping("/{employeeId}")
	    public ResponseEntity<LeaveBalance> getLeaveBalance(@PathVariable Long employeeId) {
	        LeaveBalance leaveBalance = leaveBalanceService.getLeaveBalance(employeeId);
	        return ResponseEntity.ok(leaveBalance);
	    }
	}



