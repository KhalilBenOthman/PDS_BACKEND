package com.spsrh.userService.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spsrh.userService.dto.AssignTeamDTO;
import com.spsrh.userService.dto.ManagerDTO;
import com.spsrh.userService.dto.SalarieDTO;
import com.spsrh.userService.dto.UserCredentials;
import com.spsrh.userService.model.Manager;
import com.spsrh.userService.model.Role;
import com.spsrh.userService.model.Salarie;
import com.spsrh.userService.repository.ManagerRepository;
import com.spsrh.userService.repository.SalarieRepository;
import com.spsrh.userService.service.ManagerService;
import com.spsrh.userService.service.SalarieService;

@Service
public class SalarieServiceImpl implements SalarieService {

    private final SalarieRepository salarieRepository;
    private final ManagerRepository managerRepository;
    @Autowired
    private MapValidationErrorService validationService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    public SalarieServiceImpl(SalarieRepository salarieRepository, ManagerRepository managerRepository) {
        this.salarieRepository = salarieRepository;
        this.managerRepository = managerRepository;
    }

    @Override
    public  ResponseEntity<?>  createSalarie(SalarieDTO salarieDTO) {
    	
        // Validate the input
        Map<String, String> errors = validationService.validateSalarie(salarieDTO);

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }
        Salarie salarie = new Salarie();
        salarie.setUserName(salarieDTO.getUserName());
        salarie.setFirstName(salarieDTO.getFirstName());
        salarie.setLastName(salarieDTO.getLastName());
        salarie.setEmail(salarieDTO.getEmail());
        salarie.setAdresse(salarieDTO.getAdresse());
        salarie.setPhoneNumber(salarieDTO.getPhoneNumber());
        salarie.setDepartment(salarieDTO.getDepartment());
        salarie.setEmploi(salarieDTO.getEmploi());
        salarie.setRole(salarieDTO.getRole());
        salarie.setSalary(salarieDTO.getSalary());
        salarie.setActive(salarieDTO.isActive());
        salarie.setCity(salarieDTO.getCity());
        salarie.setCodePostal(salarieDTO.getCodePostal());
        salarie.setPays(salarieDTO.getPays());
        salarie.setPassword("defaultPassword"); // Use a secure hashing algorithm later
        // If the user is not an admin, set their manager
        if (salarieDTO.getRole() != Role.ADMIN && salarieDTO.getManagerId() != null) {
            Salarie manager = salarieRepository.findById(salarieDTO.getManagerId())
                    .orElseThrow(() -> new IllegalArgumentException("Manager not found"));
            salarie.setManager(manager);
        }
        // Save the new Salarie
        Salarie savedSalarie = salarieRepository.save(salarie);
        // Add to manager's team if it's not an admin and has a manager ID
        if (savedSalarie.getRole() != Role.ADMIN && savedSalarie.getManager() != null) {
            addToManagerTeam(savedSalarie);
        }
        if (salarieDTO.getRole() == Role.MANAGER) {
            ManagerDTO managerDTO = new ManagerDTO();
            managerDTO.setId(salarie.getId());
            managerDTO.setTeamName(salarieDTO.getFirstName() + "'s Team"); // Example team name, customize as needed
            managerDTO.setTeamMemberIds(Collections.singletonList(salarie.getId())); // Add the new manager to their team

            // Create the manager using the ManagerService
            Manager createdManager = managerService.createManager(managerDTO);
        } 
        return ResponseEntity.ok(savedSalarie);
    }
    private void addToManagerTeam(Salarie salarie) {
    	Long managerId = salarie.getManager().getId();

        // Find the Manager
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new IllegalArgumentException("Manager not found"));

        // Add the new Salarie to the manager's team
        List<Salarie> teamMembers = manager.getTeamMembers();
        if (!teamMembers.contains(salarie)) {
            teamMembers.add(salarie);
            manager.setTeamMembers(teamMembers);
            managerRepository.save(manager);
        }
    }
    @Override
    public List<Salarie> getAllSalaries() {
        return salarieRepository.findAll();
    }

    @Override
    public Salarie assignTeam(AssignTeamDTO assignTeamDTO) {
        Salarie manager = salarieRepository.findById(assignTeamDTO.getManagerId())
                .orElseThrow(() -> new IllegalArgumentException("Manager not found"));

        if (manager.getRole() != Role.MANAGER) {
            throw new IllegalArgumentException("Specified salarie is not a manager.");
        }

        List<Salarie> team = salarieRepository.findAllById(assignTeamDTO.getTeamMemberIds());
        for (Salarie member : team) {
            member.setManager(manager);
        }

        return salarieRepository.save(manager);
    }

    @Override
    public Salarie getSalarieById(Long id) {
        return salarieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Salarie not found"));
    }
    
    @Override
    public boolean validateCredentials(UserCredentials credentials) {
        //Salarie salarie = salarieRepository.findByEmail(credentials.getUsername());
    	Salarie salarie = salarieRepository.findByUserName(credentials.getUsername());
        if (salarie != null && salarie.getPassword().equals(credentials.getPassword())) {
            return true;
        }
        return false;
    }
    @Override
    public Salarie updateSalarie(Long id, SalarieDTO salarieDTO) {
        Salarie salarie = getSalarieById(id);
        salarie.setFirstName(salarieDTO.getFirstName());
        salarie.setLastName(salarieDTO.getLastName());
        salarie.setEmail(salarieDTO.getEmail());
        salarie.setAdresse(salarieDTO.getAdresse());
        salarie.setPhoneNumber(salarieDTO.getPhoneNumber());
        salarie.setDepartment(salarieDTO.getDepartment());
        salarie.setEmploi(salarieDTO.getEmploi());
        salarie.setSalary(salarieDTO.getSalary());
        salarie.setRole(salarieDTO.getRole());
        salarie.setActive(salarieDTO.isActive());

        // Check if the manager has changed
        if (salarieDTO.getRole() != Role.ADMIN && salarieDTO.getManagerId() != null) {
            Salarie newManager = salarieRepository.findById(salarieDTO.getManagerId())
                    .orElseThrow(() -> new IllegalArgumentException("Manager not found with ID: " + salarieDTO.getManagerId()));
            
            // Check if the manager has changed
            if (salarie.getManager() != null && !salarie.getManager().equals(newManager)) {
                // Remove the salarie from the previous manager's team
                Manager oldManager = managerRepository.findById(salarie.getManager().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Old manager not found with ID: " + salarie.getManager().getId()));
                oldManager.getTeamMembers().remove(salarie);
                managerRepository.save(oldManager);
            }

            // Set the new manager and add the salarie to the new manager's team
            salarie.setManager(newManager);
            Manager newManagerEntity = managerRepository.findById(newManager.getId())
                    .orElseThrow(() -> new IllegalArgumentException("New manager not found with ID: " + newManager.getId()));
            newManagerEntity.getTeamMembers().add(salarie);
            managerRepository.save(newManagerEntity);
        }
        return salarieRepository.save(salarie);
    }

    @Override
    public void deleteSalarie(Long id) {
        salarieRepository.deleteById(id);
    }
}