package com.spsrh.absService.service.impl;

import com.spsrh.absService.dto.EmployeeDTO;
import com.spsrh.absService.dto.LeaveBalanceDTO;
import com.spsrh.absService.exception.ResourceNotFoundException;
import com.spsrh.absService.exception.ResourceAlreadyExistsException;
import com.spsrh.absService.mapper.LeaveBalanceMapper;
import com.spsrh.absService.model.LeaveBalance;
import com.spsrh.absService.repository.LeaveBalanceRepository;
import com.spsrh.absService.service.LeaveBalanceService;
import com.spsrh.absService.feign.EmployeFeignClient;
import com.spsrh.absService.model.User; // Model from the Feign client
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class LeaveBalanceServiceImpl implements LeaveBalanceService {

    private final LeaveBalanceRepository leaveBalanceRepository;
    private final LeaveBalanceMapper leaveBalanceMapper;
    private final EmployeFeignClient employeFeignClient;

    public LeaveBalanceServiceImpl(LeaveBalanceRepository leaveBalanceRepository,
                                   LeaveBalanceMapper leaveBalanceMapper,
                                   EmployeFeignClient employeFeignClient) {
        this.leaveBalanceRepository = leaveBalanceRepository;
        this.leaveBalanceMapper = leaveBalanceMapper;
        this.employeFeignClient = employeFeignClient;
    }

    /*@Override
    public LeaveBalanceDTO getLeaveBalanceByEmployeeUsername(String username) {
        validateEmployeeByUsername(username); // Ensure the employee exists
        LeaveBalance leaveBalance = leaveBalanceRepository.findByEmployeeUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Leave balance not found for username: " + username));
        return leaveBalanceMapper.toDTO(leaveBalance);
    }*/

    @Override
    public List<LeaveBalanceDTO> getAllLeaveBalances() {
        List<LeaveBalance> leaveBalances = leaveBalanceRepository.findAll();
        return leaveBalances.stream()
                .map(leaveBalanceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LeaveBalanceDTO updateLeaveBalance(String username, LeaveBalanceDTO leaveBalanceDTO) {
        validateEmployeeByUsername(username); // Ensure the employee exists
        LeaveBalance leaveBalance = leaveBalanceRepository.findByEmployeeUsernameAndLeaveType(username,leaveBalanceDTO.getLeaveType())
                .orElseThrow(() -> new ResourceNotFoundException("Leave balance not found for username: " + username));
        leaveBalance.setLeaveType(leaveBalanceDTO.getLeaveType());
        leaveBalance.setRemainingDays(leaveBalanceDTO.getRemainingDays());
        leaveBalance.setTotalDays(leaveBalanceDTO.getTotalDays());
        leaveBalanceRepository.save(leaveBalance);
        return leaveBalanceMapper.toDTO(leaveBalance);
    }

    @Override
    public void resetAllLeaveBalances() {
        List<LeaveBalance> leaveBalances = leaveBalanceRepository.findAll();
        leaveBalances.forEach(balance -> balance.setRemainingDays(balance.getTotalDays()));
        leaveBalanceRepository.saveAll(leaveBalances);
    }

    /**
     * Validate if the employee exists by username using the Feign client.
     * 
     * @param username the username to validate
     */
    private void validateEmployeeByUsername(String username) {
    	 // Call the Feign client to get the employee data
        ResponseEntity<EmployeeDTO> responseEntity = employeFeignClient.getEmploye(username);

        // Check if the response is successful (status code 200 OK)
        if (!responseEntity.getStatusCode().is2xxSuccessful() || responseEntity.getBody() == null) {
            // Throw exception if the employee is not found or if the response is not successful
            throw new ResourceNotFoundException("Employee not found with username: " + username);
        }
    }

	@Override
	public LeaveBalance createLeaveBalance(LeaveBalanceDTO leaveBalanceDTO) {
		 // Validate if the employee exists using their username
        validateEmployeeByUsername(leaveBalanceDTO.getEmployeeUsername());
        //Optional<LeaveBalance> leaveBalance1= leaveBalanceRepository.findByEmployeeUsernameAndLeaveType(leaveBalanceDTO.getEmployeeUsername(), leaveBalanceDTO.getLeaveType());
        Optional<LeaveBalance> existingLeaveBalance = leaveBalanceRepository.findByEmployeeUsernameAndLeaveType(
                leaveBalanceDTO.getEmployeeUsername(), leaveBalanceDTO.getLeaveType());

        if (existingLeaveBalance.isPresent()) {
            throw new ResourceAlreadyExistsException("Leave balance with type " + leaveBalanceDTO.getLeaveType() +
                    " already exists for user: " + leaveBalanceDTO.getEmployeeUsername());
        }
        // Convert DTO to entity
        LeaveBalance leaveBalance = new LeaveBalance();
        leaveBalance.setEmployeeUsername(leaveBalanceDTO.getEmployeeUsername()); // Assuming the entity expects employeeId
        leaveBalance.setLeaveType(leaveBalanceDTO.getLeaveType());
        leaveBalance.setRemainingDays(leaveBalanceDTO.getRemainingDays());
        leaveBalance.setTotalDays(leaveBalanceDTO.getTotalDays());

        // Save the entity
        LeaveBalance savedLeaveBalance = leaveBalanceRepository.save(leaveBalance);

        // Convert entity back to DTO
        return savedLeaveBalance;
	}

	@Override
	public List<LeaveBalance> getLeaveBalanceListByEmployeeUsername(String username) {
        validateEmployeeByUsername(username); // Ensure the employee exists
        List<LeaveBalance> leaveBalance =leaveBalanceRepository.findByEmployeeUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Leave balance not found for username: " + username));
        return leaveBalance;
	}
}
