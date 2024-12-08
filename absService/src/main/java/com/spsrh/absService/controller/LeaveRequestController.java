package com.spsrh.absService.controller;

import java.util.List;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spsrh.absService.dto.CreateLeaveRequestDTO;
import com.spsrh.absService.dto.LeaveRequestDTO;
import com.spsrh.absService.dto.UpdateLeaveStatusDTO;
import com.spsrh.absService.model.LeaveStatus;
import com.spsrh.absService.service.LeaveRequestService;

@RestController
	@RequestMapping("/api/leave-requests")
	public class LeaveRequestController {

	    @Autowired
	    private LeaveRequestService leaveRequestService;

	    // 1. Créer une demande de congé
	    @PostMapping
	    public ResponseEntity<LeaveRequestDTO> createLeaveRequest(@RequestBody CreateLeaveRequestDTO createLeaveRequestDTO) {
	        LeaveRequestDTO leaveRequestDTO = leaveRequestService.createLeaveRequest(createLeaveRequestDTO);
	        return ResponseEntity.status(HttpStatus.SC_CREATED).body(leaveRequestDTO);
	    }

	    // 2. Mettre à jour le statut d'une demande
	    @PutMapping("/status")
	    public ResponseEntity<LeaveRequestDTO> updateLeaveRequestStatus(@RequestBody UpdateLeaveStatusDTO updateLeaveStatusDTO) {
	        LeaveRequestDTO updatedRequest = leaveRequestService.updateLeaveRequestStatus(updateLeaveStatusDTO);
	        return ResponseEntity.ok(updatedRequest);
	    }

	    // 3. Récupérer toutes les demandes d'un employé
	    @GetMapping("/employee/{employeeId}")
	    public ResponseEntity<List<LeaveRequestDTO>> getEmployeeLeaveRequests(@PathVariable Long employeeId) {
	        List<LeaveRequestDTO> leaveRequests = leaveRequestService.getEmployeeLeaveRequests(employeeId);
	        return ResponseEntity.ok(leaveRequests);
	    }

	    // 4. Récupérer toutes les demandes par statut
	    @GetMapping("/status/{status}")
	    public ResponseEntity<List<LeaveRequestDTO>> getLeaveRequestsByStatus(@PathVariable LeaveStatus status) {
	        List<LeaveRequestDTO> leaveRequests = leaveRequestService.getLeaveRequestsByStatus(status);
	        return ResponseEntity.ok(leaveRequests);
	    }
	}



