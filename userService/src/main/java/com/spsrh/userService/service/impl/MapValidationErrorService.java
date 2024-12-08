package com.spsrh.userService.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.spsrh.userService.dto.SalarieDTO;

import java.util.HashMap;
import java.util.Map;

@Service
public class MapValidationErrorService {

    public Map<String, String> validateSalarie(SalarieDTO salarieDTO) {
        Map<String, String> errors = new HashMap<>();
        
        if (salarieDTO.getUserName() == null || salarieDTO.getUserName().isEmpty()) {
            errors.put("userName", "User name cannot be empty");
        }
        if (salarieDTO.getEmail() == null || salarieDTO.getEmail().isEmpty()) {
            errors.put("email", "Email cannot be empty");
        }
        // Add more validations as needed
        if (salarieDTO.getPhoneNumber() != null && !salarieDTO.getPhoneNumber().matches("\\d+")) {
            errors.put("phoneNumber", "Phone number must be numeric");
        }

        return errors;
    }
    
	/*
	 * public Map<String, String> mapValidationError(BindingResult result) {
	 * Map<String, String> errorMap = new HashMap<>();
	 * 
	 * for (FieldError error : result.getFieldErrors()) {
	 * errorMap.put(error.getField(), error.getDefaultMessage()); }
	 * 
	 * return errorMap; }
	 */
    public ResponseEntity<?> mapValidationError(BindingResult result) {
        if (result.hasErrors()) {
            // Create a map of error messages and return it as a ResponseEntity
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> 
                errors.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(errors);
        }
        return null; // No errors
    }
}
