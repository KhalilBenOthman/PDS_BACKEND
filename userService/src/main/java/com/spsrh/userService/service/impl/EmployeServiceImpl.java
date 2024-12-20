package com.spsrh.userService.service.impl;

import com.spsrh.userService.dto.EmployeeDTO;
import com.spsrh.userService.exception.EmployeNotFoundException;
import com.spsrh.userService.exception.InvalidAssignationException;
import com.spsrh.userService.model.Employee;
import com.spsrh.userService.model.Manager;
import com.spsrh.userService.repository.EmployeRepository;
import com.spsrh.userService.repository.ManagerRepository;
import com.spsrh.userService.service.EmployeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeServiceImpl implements EmployeService {

    private final EmployeRepository employeRepository;
    private final ManagerRepository managerRepository;
    private final ModelMapper modelMapper;

    public EmployeServiceImpl(EmployeRepository employeRepository, 
                              ManagerRepository managerRepository,
                              ModelMapper modelMapper) {
        this.employeRepository = employeRepository;
        this.managerRepository = managerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmployeeDTO getEmployeByUsername(String username) {
        Employee employe = employeRepository.findByUsername(username);
        if (employe == null) {
            throw new EmployeNotFoundException(username);
        }
        return modelMapper.map(employe, EmployeeDTO.class);
    }

    @Override
    public List<EmployeeDTO> getAllEmployes() {
        return employeRepository.findAll().stream()
                .map(employe -> modelMapper.map(employe, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void assignManager(String employeUsername, String managerUsername) {
        Employee employe = employeRepository.findByUsername(employeUsername);
        Manager manager = managerRepository.findByUsername(managerUsername);

        if (employe == null || manager == null) {
            throw new InvalidAssignationException("Invalid Employee or Manager username");
        }

        employe.setManager(manager);
        employeRepository.save(employe);
    }
}
