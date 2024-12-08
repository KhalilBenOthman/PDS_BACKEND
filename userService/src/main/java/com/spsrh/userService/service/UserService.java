package com.spsrh.userService.service;

import com.spsrh.userService.model.User;
import com.spsrh.userService.dto.UserCredentials;
import com.spsrh.userService.dto.UserDTO;
import java.util.List;
import java.util.Optional;

import org.springframework.validation.BindingResult;

public interface UserService {
    User createUser(UserDTO userDTO, BindingResult result);
    Optional<User> getUserById(Long userId);
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);
    List<User> getAllUsers();
    User updateUser(UserDTO userDTO, BindingResult result);
    void deleteUser(Long userId);
    void createManagerTeam(User manager);
    
    boolean validateCredentials(UserCredentials userCrendentials);
}
