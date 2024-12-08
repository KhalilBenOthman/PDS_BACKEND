package com.spsrh.userService.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spsrh.userService.dto.UserCredentials;
import com.spsrh.userService.dto.UserDTO;
import com.spsrh.userService.model.User;
import com.spsrh.userService.service.UserService;
import com.spsrh.userService.service.impl.MapValidationErrorService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
    private UserService userService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;
    
    
    @GetMapping("health-check")
    public String healthCheck() {
      return "UserService - Everythings is okey";
    }
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult result) {
        // Check for validation errors
    	 ResponseEntity<?> errorResponse = mapValidationErrorService.mapValidationError(result);
       if (errorResponse != null) {
            return errorResponse;
        }

        User createdUser = userService.createUser(userDTO, result);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.getUserByUsername(username);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDTO userDTO, BindingResult result) {
        // Check for validation errors
        ResponseEntity<?> errorResponse = (ResponseEntity<?>) mapValidationErrorService.mapValidationError(result);
        if (errorResponse != null) {
            return errorResponse;
        }

        User updatedUser = userService.updateUser(userDTO, result);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/validate")
    public Boolean validateUser(@RequestBody UserCredentials credentials) {
        return userService.validateCredentials(credentials);
    }
    
    
    
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
