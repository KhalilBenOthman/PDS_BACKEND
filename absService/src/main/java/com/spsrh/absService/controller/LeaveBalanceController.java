package com.spsrh.absService.controller;

import com.spsrh.absService.dto.LeaveBalanceDTO;
import com.spsrh.absService.service.LeaveBalanceService;
import com.spsrh.absService.exception.ResourceNotFoundException;
import com.spsrh.absService.model.LeaveBalance;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-balances")
public class LeaveBalanceController {

    private final LeaveBalanceService leaveBalanceService;

    public LeaveBalanceController(LeaveBalanceService leaveBalanceService) {
        this.leaveBalanceService = leaveBalanceService;
    }

    // Get leave balance for a specific employee by username
    @GetMapping("/username/{username}")
    public ResponseEntity<List<LeaveBalance>> getLeaveBalanceByEmployeeUsername(@PathVariable String username) {
        try {
        	List<LeaveBalance> leaveBalanceDTO = leaveBalanceService.getLeaveBalanceListByEmployeeUsername(username);
            return ResponseEntity.ok(leaveBalanceDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Get all leave balances
    @GetMapping
    public ResponseEntity<List<LeaveBalanceDTO>> getAllLeaveBalances() {
        List<LeaveBalanceDTO> leaveBalances = leaveBalanceService.getAllLeaveBalances();
        return ResponseEntity.ok(leaveBalances);
    }

    // Update leave balance for a specific employee by username
    @PutMapping("/username/{username}")
    public ResponseEntity<LeaveBalanceDTO> updateLeaveBalance(@PathVariable String username, @RequestBody LeaveBalanceDTO leaveBalanceDTO) {
        try {
            LeaveBalanceDTO updatedLeaveBalance = leaveBalanceService.updateLeaveBalance(username, leaveBalanceDTO);
            return ResponseEntity.ok(updatedLeaveBalance);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    // Create a new leave balance for an employee
    @PostMapping("/create")
    public ResponseEntity<?> createLeaveBalance(@RequestBody LeaveBalanceDTO leaveBalanceDTO) {
        /*try {
            LeaveBalance createdLeaveBalance = leaveBalanceService.createLeaveBalance(leaveBalanceDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLeaveBalance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }*/
    	LeaveBalance createdLeaveBalance = leaveBalanceService.createLeaveBalance(leaveBalanceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLeaveBalance);
    }
    // Reset all leave balances
    @PutMapping("/reset")
    public ResponseEntity<Void> resetAllLeaveBalances() {
        leaveBalanceService.resetAllLeaveBalances();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
