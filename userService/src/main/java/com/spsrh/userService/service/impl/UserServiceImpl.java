package com.spsrh.userService.service.impl;

import com.spsrh.userService.dto.UserCredentials;
import com.spsrh.userService.dto.UserDTO;
import com.spsrh.userService.model.User;
import com.spsrh.userService.model.ManagerTeam;
import com.spsrh.userService.model.Role;
import com.spsrh.userService.model.Salarie;
import com.spsrh.userService.repository.UserRepository;
import com.spsrh.userService.repository.ManagerTeamRepository;
import com.spsrh.userService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ManagerTeamRepository managerTeamRepository;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Transactional
    @Override
    public User createUser(UserDTO userDTO, BindingResult result) {
    	
    	 // Check for validation errors
        if (result.hasErrors()) {
            Map<String, String> errorMap = (Map<String, String>) mapValidationErrorService.mapValidationError(result);
            // Handle or throw an exception based on the error map if needed
            throw new IllegalArgumentException("Validation errors: " + errorMap);
        }
    	
    	
        // Convert DTO to Entity
        User user = new User();
        user.setUsername(userDTO.getUsername());
        
        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPasswordHash(hashedPassword);
        
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        user.setIsActive(userDTO.getIsActive());
        user.setAdresse(userDTO.getAdresse());
        user.setCity(userDTO.getCity());
        user.setCodePostal(userDTO.getCodePostal());
        user.setPays(userDTO.getPays());
        user.setPhoneNumber(userDTO.getPhoneNumber());

        // Save the user to the database
        User savedUser = userRepository.save(user);

        // If the user is a manager, create a ManagerTeam
        if (savedUser.getRole() != null && savedUser.getRole().equals(Role.MANAGER)) {
        	 createManagerTeam(savedUser);
        }

        return savedUser;
    }

    @Override
    public User updateUser(UserDTO userDTO, BindingResult result) {
        
    	 // Check for validation errors
        if (result.hasErrors()) {
            Map<String, String> errorMap = (Map<String, String>) mapValidationErrorService.mapValidationError(result);
            // Handle or throw an exception based on the error map if needed
            throw new IllegalArgumentException("Validation errors: " + errorMap);
        }
    	
    	// Find existing user by ID
        Optional<User> optionalUser = userRepository.findById(userDTO.getUserId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Update fields from DTO
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setRole(userDTO.getRole());
            user.setIsActive(userDTO.getIsActive());
            user.setAdresse(userDTO.getAdresse());
            user.setCity(userDTO.getCity());
            user.setCodePostal(userDTO.getCodePostal());
            user.setPays(userDTO.getPays());
            user.setPhoneNumber(userDTO.getPhoneNumber());

            // Save updated user to the database
            return userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + userDTO.getUserId());
        }
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return Optional.ofNullable(userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId)));
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username)));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email)));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        userRepository.delete(user);
    }

    @Override
    public void createManagerTeam(User manager) {
        if (manager.getRole() != null && manager.getRole().equals(Role.MANAGER)) {
            // Create and save a new ManagerTeam associated with the manager
            ManagerTeam managerTeam = new ManagerTeam();
            managerTeam.setManager(manager);
            managerTeamRepository.save(managerTeam);
        } else {
            throw new IllegalArgumentException("User is not a manager, cannot create a team.");
        }
    }
    
    
    @Override
    public boolean validateCredentials(UserCredentials credentials) {
        Optional<User> userOptional = userRepository.findByUsername(credentials.getUsername());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Use the password encoder to match the raw password with the hashed one in the database
            return passwordEncoder.matches(credentials.getPassword(), user.getPasswordHash());
        }
        return false;
    }
    
    
    
    
    
    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}
