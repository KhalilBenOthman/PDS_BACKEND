package com.spsrh.userService.service.impl;

import com.spsrh.userService.dto.ManagerDTO;
import com.spsrh.userService.model.Manager;
import com.spsrh.userService.model.Salarie;
import com.spsrh.userService.repository.ManagerRepository;
import com.spsrh.userService.repository.SalarieRepository;
import com.spsrh.userService.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private SalarieRepository salarieRepository;

    @Override
    public Manager createManager(ManagerDTO managerDTO) {
        Manager manager = new Manager();
        manager.setId(managerDTO.getId());
        manager.setTeamName(managerDTO.getTeamName());

        // Fetch team members from their IDs and assign them to the manager
        List<Salarie> teamMembers = managerDTO.getTeamMemberIds().stream()
                .map(id -> salarieRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Salarie not found with ID: " + id)))
                .collect(Collectors.toList());
        manager.setTeamMembers(teamMembers);

        return managerRepository.save(manager);
    }
    
    @Override
    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    @Override
    public Manager getManagerById(Long id) {
        return managerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Manager not found with ID: " + id));
    }

    @Override
    public Manager updateManager(Long id, ManagerDTO managerDTO) {
        Manager manager = getManagerById(id);
        manager.setTeamName(managerDTO.getTeamName());

        List<Salarie> teamMembers = managerDTO.getTeamMemberIds().stream()
                .map(memberId -> salarieRepository.findById(memberId)
                        .orElseThrow(() -> new IllegalArgumentException("Salarie not found with ID: " + memberId)))
                .collect(Collectors.toList());
        manager.setTeamMembers(teamMembers);

        return managerRepository.save(manager);
    }

    @Override
    public void deleteManager(Long id) {
        managerRepository.deleteById(id);
    }
}
