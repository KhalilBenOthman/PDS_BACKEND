package com.spsrh.absService.service;

import java.util.List;

import com.spsrh.absService.dto.CreateLeaveRequestDTO;
import com.spsrh.absService.dto.LeaveRequestDTO;
import com.spsrh.absService.dto.UpdateLeaveStatusDTO;
import com.spsrh.absService.model.LeaveStatus;

public interface LeaveRequestService {
	
	
	    LeaveRequestDTO createLeaveRequest(CreateLeaveRequestDTO createLeaveRequestDTO);
	    LeaveRequestDTO updateLeaveRequestStatus(UpdateLeaveStatusDTO updateLeaveStatusDTO);
	    List<LeaveRequestDTO> getEmployeeLeaveRequests(Long employeeId);
	    List<LeaveRequestDTO> getLeaveRequestsByStatus(LeaveStatus status);
	}



