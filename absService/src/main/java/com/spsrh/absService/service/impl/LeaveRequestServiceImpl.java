package com.spsrh.absService.service.impl;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spsrh.absService.dto.CreateLeaveRequestDTO;
import com.spsrh.absService.dto.LeaveRequestDTO;
import com.spsrh.absService.dto.UpdateLeaveStatusDTO;
import com.spsrh.absService.model.Employee;
import com.spsrh.absService.model.LeaveBalance;
import com.spsrh.absService.model.LeaveRequest;
import com.spsrh.absService.model.LeaveStatus;
import com.spsrh.absService.repository.EmployeeRepository;
import com.spsrh.absService.repository.LeaveRequestRepository;
import com.spsrh.absService.service.LeaveBalanceService;
import com.spsrh.absService.service.LeaveRequestService;

@Service

public class LeaveRequestServiceImpl implements LeaveRequestService {
	
	


	    @Autowired
	    private LeaveRequestRepository leaveRequestRepository;

	    @Autowired
	    private EmployeeRepository employeeRepository;

	    @Autowired
	    private LeaveBalanceService leaveBalanceService;

	    @Override
	    public LeaveRequestDTO createLeaveRequest(CreateLeaveRequestDTO createLeaveRequestDTO) {
	        Employee employee = employeeRepository.findById(createLeaveRequestDTO.getEmployeeId())
	            .orElseThrow(() -> new RuntimeException("Employee not found"));

	        // Vérifier le solde de congés
	        LeaveBalance leaveBalance = leaveBalanceService.getLeaveBalance(employee.getId());
	        int daysRequested = (int) ChronoUnit.DAYS.between(
	            createLeaveRequestDTO.getStartDate(),
	            createLeaveRequestDTO.getEndDate()
	        );
	        if (leaveBalance.getRemainingLeaves() < daysRequested) {
	            throw new RuntimeException("Not enough leave balance");
	        }

	        LeaveRequest leaveRequest = new LeaveRequest();
	        leaveRequest.setEmployee(employee);
	        leaveRequest.setLeaveType(createLeaveRequestDTO.getLeaveType());
	        leaveRequest.setStartDate(createLeaveRequestDTO.getStartDate());
	        leaveRequest.setEndDate(createLeaveRequestDTO.getEndDate());
	        leaveRequest.setReason(createLeaveRequestDTO.getReason());
	        leaveRequest.setStatus(LeaveStatus.PENDING);

	        LeaveRequest savedRequest = leaveRequestRepository.save(leaveRequest);
	        return mapToDTO(savedRequest);
	    }

	    @Override
	    public LeaveRequestDTO updateLeaveRequestStatus(UpdateLeaveStatusDTO updateLeaveStatusDTO) {
	        LeaveRequest leaveRequest = leaveRequestRepository.findById(updateLeaveStatusDTO.getLeaveRequestId())
	            .orElseThrow(() -> new RuntimeException("Leave request not found"));

	        leaveRequest.setStatus(updateLeaveStatusDTO.getStatus());
	        leaveRequest.setManagerComment(updateLeaveStatusDTO.getManagerComment());

	        if (updateLeaveStatusDTO.getStatus() == LeaveStatus.APPROVED) {
	            int daysUsed = (int) ChronoUnit.DAYS.between(
	                leaveRequest.getStartDate(),
	                leaveRequest.getEndDate()
	            );
	            leaveBalanceService.updateLeaveBalance(leaveRequest.getEmployee().getId(), daysUsed);
	        }

	        LeaveRequest updatedRequest = leaveRequestRepository.save(leaveRequest);
	        return mapToDTO(updatedRequest);
	    }

	    @Override
	    public List<LeaveRequestDTO> getEmployeeLeaveRequests(Long employeeId) {
	        return leaveRequestRepository.findByEmployeeId(employeeId)
	            .stream()
	            .map(this::mapToDTO)
	            .collect(Collectors.toList());
	    }

	    @Override
	    public List<LeaveRequestDTO> getLeaveRequestsByStatus(LeaveStatus status) {
	        return leaveRequestRepository.findByStatus(status)
	            .stream()
	            .map(this::mapToDTO)
	            .collect(Collectors.toList());
	    }

	    private LeaveRequestDTO mapToDTO(LeaveRequest leaveRequest) {
	        LeaveRequestDTO dto = new LeaveRequestDTO();
	        dto.setId(leaveRequest.getId());
	        dto.setEmployeeId(leaveRequest.getEmployee().getId());
	        dto.setEmployeeName(leaveRequest.getEmployee().getFirstName() + " " + leaveRequest.getEmployee().getLastName());
	        dto.setLeaveType(leaveRequest.getLeaveType());
	        dto.setStatus(leaveRequest.getStatus());
	        dto.setStartDate(leaveRequest.getStartDate());
	        dto.setEndDate(leaveRequest.getEndDate());
	        dto.setReason(leaveRequest.getReason());
	        dto.setManagerComment(leaveRequest.getManagerComment());
	        return dto;
	    }
	}



