package com.spsrh.userService.exception;

public class EmployeNotFoundException extends RuntimeException {

    public EmployeNotFoundException(String username) {
        super("Employee with username '" + username + "' not found.");
    }
}
