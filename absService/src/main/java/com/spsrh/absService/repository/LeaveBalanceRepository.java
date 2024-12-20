package com.spsrh.absService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spsrh.absService.model.LeaveBalance;
import com.spsrh.absService.model.LeaveType;

@Repository
public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {
   // Optional<LeaveBalance> findByEmployeeUsername(String employeeUsername);
    
    // Optional: Add custom queries here if needed
    Optional<LeaveBalance> findByEmployeeUsernameAndLeaveType(String employeeUsername, LeaveType leaveType);

    // If you want to find all leave balances for a specific employee
    Optional<List<LeaveBalance>> findByEmployeeUsername(String employeeUsername);

    // If you want to find leave balances by leave type
    List<LeaveBalance> findByLeaveType(LeaveType leaveType);
}

