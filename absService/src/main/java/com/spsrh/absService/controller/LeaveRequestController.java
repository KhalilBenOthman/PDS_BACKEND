package com.spsrh.absService.controller;

import com.spsrh.absService.dto.EmployeeDTO;
import com.spsrh.absService.dto.LeaveRequestDTO;
import com.spsrh.absService.dto.UpdateLeaveStatusDTO;
import com.spsrh.absService.model.LeaveStatus;
import com.spsrh.absService.service.LeaveRequestService;
import com.spsrh.absService.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/leave-requests")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    // Create a new leave request
    @PostMapping("/create")
    public ResponseEntity<LeaveRequestDTO> createLeaveRequest(@RequestBody LeaveRequestDTO leaveRequestDTO) {
        try {
            LeaveRequestDTO createdLeaveRequest = leaveRequestService.createLeaveRequest(leaveRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLeaveRequest);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PostMapping
    public ResponseEntity<LeaveRequestDTO> createLeaveRequestWithToken(@RequestHeader("Authorization") String token,@RequestBody LeaveRequestDTO leaveRequestDTO) {
    	String userName = leaveRequestService.getUserFromToken(token);
		//try {
			leaveRequestDTO.setEmployeeUsername(userName);
		   LeaveRequestDTO createdLeaveRequest = leaveRequestService.createLeaveRequest(leaveRequestDTO);
		   return ResponseEntity.status(HttpStatus.CREATED).body(createdLeaveRequest);
		/*} catch (ResourceNotFoundException e) {
		   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}*/
    	
    }
    
    
    
    // Get all leave requests for a specific employee by username
    @GetMapping("/employee/{username}")
     public ResponseEntity<List<LeaveRequestDTO>> getLeaveRequestsByEmployeeUsername(@PathVariable String username) {
        try {
            List<LeaveRequestDTO> leaveRequests = leaveRequestService.getLeaveRequestsByEmployeeUsername(username);
            return ResponseEntity.ok(leaveRequests);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Get all leave requests for a specific manager by username
    @GetMapping("/manager/{username}")
    public ResponseEntity<List<LeaveRequestDTO>> getLeaveRequestsByManagerUsername(@PathVariable String username) {
        try {
            List<LeaveRequestDTO> leaveRequests = leaveRequestService.getLeaveRequestsByManagerUsername(username);
            return ResponseEntity.ok(leaveRequests);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Update the status of a leave request (approve/reject)
    @PutMapping("/{leaveRequestId}/status")
    public ResponseEntity<LeaveRequestDTO> updateLeaveRequestStatus(@PathVariable Long leaveRequestId, @RequestBody UpdateLeaveStatusDTO updateLeaveStatusDTO) {
        try {
            LeaveRequestDTO updatedLeaveRequest = leaveRequestService.updateLeaveRequestStatus(leaveRequestId, updateLeaveStatusDTO.getStatus());
            return ResponseEntity.ok(updatedLeaveRequest);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Get leave requests by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<LeaveRequestDTO>> getLeaveRequestsByStatus(@PathVariable LeaveStatus status) {
        List<LeaveRequestDTO> leaveRequests = leaveRequestService.getLeaveRequestsByStatus(status);
        return ResponseEntity.ok(leaveRequests);
    }
}
