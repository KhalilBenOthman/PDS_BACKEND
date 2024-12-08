package com.spsrh.absService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spsrh.absService.model.LeaveRequest;
import com.spsrh.absService.model.LeaveStatus;

@Repository
	public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
	    List<LeaveRequest> findByEmployeeId(Long employeeId);
	    List<LeaveRequest> findByStatus(LeaveStatus status);
	}



