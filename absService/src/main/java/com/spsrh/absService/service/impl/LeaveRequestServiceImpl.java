package com.spsrh.absService.service.impl;

import com.spsrh.absService.dto.EmployeeDTO;
import com.spsrh.absService.dto.LeaveRequestDTO;
import com.spsrh.absService.exception.ResourceNotFoundException;
import com.spsrh.absService.exception.InsufficientLeaveBalanceException;
import com.spsrh.absService.mapper.LeaveRequestMapper;
import com.spsrh.absService.model.LeaveBalance;
import com.spsrh.absService.model.LeaveRequest;
import com.spsrh.absService.model.LeaveStatus;
import com.spsrh.absService.model.User;
import com.spsrh.absService.repository.LeaveBalanceRepository;
import com.spsrh.absService.repository.LeaveRequestRepository;
import com.spsrh.absService.service.LeaveRequestService;
import com.spsrh.absService.feign.AuthServiceFeignClient;
import com.spsrh.absService.feign.EmployeFeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final LeaveRequestMapper leaveRequestMapper;
    private final EmployeFeignClient employeFeignClient;
    private final LeaveBalanceRepository leaveBalanceRepository;
	private final AuthServiceFeignClient authSeriveFeignClient;
	
    @Autowired
    public LeaveRequestServiceImpl(LeaveRequestRepository leaveRequestRepository,
                                   LeaveRequestMapper leaveRequestMapper,
                                   EmployeFeignClient employeFeignClient,
                                   LeaveBalanceRepository leaveBalanceRepository,
                                   AuthServiceFeignClient authSeriveFeignClient) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.leaveRequestMapper = leaveRequestMapper;
        this.employeFeignClient = employeFeignClient;
        this.leaveBalanceRepository = leaveBalanceRepository;
        this.authSeriveFeignClient = authSeriveFeignClient;
    }

    // Helper method to validate if employee exists by username
    private void validateEmployeeByUsername(String username) {
   	 // Call the Feign client to get the employee data
        ResponseEntity<EmployeeDTO> responseEntity = employeFeignClient.getEmploye(username);

        // Check if the response is successful (status code 200 OK)
        if (!responseEntity.getStatusCode().is2xxSuccessful() || responseEntity.getBody() == null) {
            // Throw exception if the employee is not found or if the response is not successful
            throw new ResourceNotFoundException("Employee not found with username: " + username);
        }
    }
    
    private EmployeeDTO GetEmployeeByUsername(String username) {
      	 // Call the Feign client to get the employee data
           ResponseEntity<EmployeeDTO> responseEntity = employeFeignClient.getEmploye(username);
           
           // Check if the response is successful (status code 200 OK)
           if (!responseEntity.getStatusCode().is2xxSuccessful() || responseEntity.getBody() == null) {
               // Throw exception if the employee is not found or if the response is not successful
               throw new ResourceNotFoundException("Employee not found with username: " + username);
           }
           return  responseEntity.getBody();
       }

    @Override
    public LeaveRequestDTO createLeaveRequest(LeaveRequestDTO leaveRequestDTO) {
        // Validate employee existence before creating leave request
        validateEmployeeByUsername(leaveRequestDTO.getEmployeeUsername());
        EmployeeDTO employeeDTO = GetEmployeeByUsername(leaveRequestDTO.getEmployeeUsername());
        // Calculate the leave days based on start and end dates
        long leaveDays = calculateLeaveDays(leaveRequestDTO.getStartDate(), leaveRequestDTO.getEndDate());
        LeaveBalance leaveBalance = leaveBalanceRepository.findByEmployeeUsernameAndLeaveType(
                leaveRequestDTO.getEmployeeUsername(), leaveRequestDTO.getLeaveType())
                .orElseThrow(() -> new ResourceNotFoundException("Leave balance not found for user: " 
                        + leaveRequestDTO.getEmployeeUsername() + " and leave type: " + leaveRequestDTO.getLeaveType()));
        
        // Check if the employee has enough remaining leave days
        if (leaveBalance.getRemainingDays() < leaveDays) {
            throw new InsufficientLeaveBalanceException("Insufficient leave balance for user: "
                    + leaveRequestDTO.getEmployeeUsername() + " for leave type: " + leaveRequestDTO.getLeaveType());
        }
        // Map DTO to entity and save it
        LeaveRequest leaveRequest = leaveRequestMapper.toEntity(leaveRequestDTO);
        leaveRequest.setManagerUsername(employeeDTO.getManagerUsername());
        leaveRequest = leaveRequestRepository.save(leaveRequest);
        
     // Update the leave balance by deducting the used leave days
        leaveBalance.setRemainingDays(leaveBalance.getRemainingDays() - (int) leaveDays);
        leaveBalanceRepository.save(leaveBalance);
        
        // Return saved DTO
        return leaveRequestMapper.toDTO(leaveRequest);
    }

    @Override
    public List<LeaveRequestDTO> getLeaveRequestsByEmployeeUsername(String username) {
        // Validate employee existence before fetching leave requests
        validateEmployeeByUsername(username);

        // Fetch leave requests for the employee and convert to DTOs
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findByEmployeeUsername(username);
        return leaveRequests.stream()
                .map(leaveRequestMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LeaveRequestDTO> getLeaveRequestsByManagerUsername(String username) {
        // Validate manager existence before fetching leave requests
        validateEmployeeByUsername(username);

        // Fetch leave requests for the manager's employees and convert to DTOs
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findByManagerUsername(username);
        return leaveRequests.stream()
                .map(leaveRequestMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LeaveRequestDTO updateLeaveRequestStatus(Long leaveRequestId, LeaveStatus status) {
        // Fetch the leave request and check if it exists
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found with ID: " + leaveRequestId));

        // Update the status of the leave request
        leaveRequest.setLeaveStatus(status);
        leaveRequest = leaveRequestRepository.save(leaveRequest);

        // Return the updated leave request as DTO
        return leaveRequestMapper.toDTO(leaveRequest);
    }

    @Override
    public List<LeaveRequestDTO> getLeaveRequestsByStatus(LeaveStatus status) {
        // Fetch leave requests with the specified status and convert to DTOs
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findByLeaveStatus(status);
        return leaveRequests.stream()
                .map(leaveRequestMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    
    private long calculateLeaveDays(LocalDate startDate, LocalDate endDate) {
        // Ensure the startDate is before or equal to the endDate
        if (startDate != null && endDate != null && !startDate.isAfter(endDate)) {
            return java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1; // Including the end day
        } else {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }
    }
    
	public String getUserFromToken(String token) {
		String response = authSeriveFeignClient.getUserFromToken(token);
		return response;
	}
}
