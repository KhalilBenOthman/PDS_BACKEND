package com.spsrh.absService.service;

import java.util.List;

import com.spsrh.absService.dto.CreateLeaveRequestDTO;
import com.spsrh.absService.dto.LeaveRequestDTO;
import com.spsrh.absService.dto.UpdateLeaveStatusDTO;
import com.spsrh.absService.model.LeaveStatus;

import java.util.List;

public interface LeaveRequestService {

    // Create a new leave request
    LeaveRequestDTO createLeaveRequest(LeaveRequestDTO leaveRequestDTO);

    // Retrieve leave requests for a specific employee
    List<LeaveRequestDTO> getLeaveRequestsByEmployeeUsername(String username);

    // Retrieve leave requests for a specific manager
    List<LeaveRequestDTO> getLeaveRequestsByManagerUsername(String username);

    // Update the status of a leave request (e.g., Approve/Reject)
    LeaveRequestDTO updateLeaveRequestStatus(Long leaveRequestId, LeaveStatus status);

    // Retrieve all leave requests with a specific status
    List<LeaveRequestDTO> getLeaveRequestsByStatus(LeaveStatus status);
    
    String getUserFromToken(String token);
}



