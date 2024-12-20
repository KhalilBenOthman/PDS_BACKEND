package com.spsrh.userService.service;

import com.spsrh.userService.dto.UserCredentials;
import com.spsrh.userService.dto.UserDTO;
import com.spsrh.userService.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    User getUserByUsername(String username);
    List<User> getAllUsers();
    void deleteUser(String username);
    User createUser(UserDTO userDTO);
	Optional<User> getUserById(Long id);

	 boolean validateCredentials(UserCredentials userCrendentials);
	User getUserBytoken(String token);
	
	Map<String, Object> getUserStatistics();
}
