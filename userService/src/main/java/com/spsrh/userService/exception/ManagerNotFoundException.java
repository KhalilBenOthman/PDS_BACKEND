package com.spsrh.userService.exception;

public class ManagerNotFoundException extends RuntimeException {

    public ManagerNotFoundException(String username) {
        super("Manager with username '" + username + "' not found.");
    }
}
