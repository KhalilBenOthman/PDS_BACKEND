package com.spsrh.userService.service.impl;

import com.spsrh.userService.dto.ManagerDTO;
import com.spsrh.userService.exception.ManagerNotFoundException;
import com.spsrh.userService.exception.InvalidAssignationException;
import com.spsrh.userService.model.Employee;
import com.spsrh.userService.model.Manager;
import com.spsrh.userService.repository.EmployeRepository;
import com.spsrh.userService.repository.ManagerRepository;
import com.spsrh.userService.service.ManagerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;
    private final EmployeRepository employeRepository;
    private final ModelMapper modelMapper;

    public ManagerServiceImpl(ManagerRepository managerRepository,
                              EmployeRepository employeRepository,
                              ModelMapper modelMapper) {
        this.managerRepository = managerRepository;
        this.employeRepository = employeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ManagerDTO getManagerByUsername(String username) {
        Manager manager = managerRepository.findByUsername(username);
        if (manager == null) {
            throw new ManagerNotFoundException(username);
        }
        return modelMapper.map(manager, ManagerDTO.class);
    }

    @Override
    public List<ManagerDTO> getAllManagers() {
        return managerRepository.findAll().stream()
                .map(manager -> modelMapper.map(manager, ManagerDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addEmployeToTeam(String managerUsername, String employeUsername) {
        Manager manager = managerRepository.findByUsername(managerUsername);
        Employee employe = employeRepository.findByUsername(employeUsername);

        if (manager == null || employe == null) {
            throw new InvalidAssignationException("Invalid Manager or Employee username");
        }

        employe.setManager(manager);
        manager.getTeam().add(employe);

        employeRepository.save(employe);
        managerRepository.save(manager);
    }

    @Override
    public void removeEmployeFromTeam(String managerUsername, String employeUsername) {
        Manager manager = managerRepository.findByUsername(managerUsername);
        Employee employe = employeRepository.findByUsername(employeUsername);

        if (manager == null || employe == null || !manager.getTeam().contains(employe)) {
            throw new InvalidAssignationException("Invalid Manager, Employee, or relationship");
        }

        manager.getTeam().remove(employe);
        employe.setManager(null);

        employeRepository.save(employe);
        managerRepository.save(manager);
    }
}
