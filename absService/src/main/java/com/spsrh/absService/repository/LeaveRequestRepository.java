package com.spsrh.absService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spsrh.absService.model.LeaveRequest;
import com.spsrh.absService.model.LeaveStatus;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
	   
    // Custom query to find LeaveRequests by employee username
    List<LeaveRequest> findByEmployeeUsername(String employeeUsername);

    // Custom query to find LeaveRequests by manager username
    List<LeaveRequest> findByManagerUsername(String managerUsername);

    // Custom query to find LeaveRequests by leave status
    List<LeaveRequest> findByLeaveStatus(LeaveStatus leaveStatus);

}



