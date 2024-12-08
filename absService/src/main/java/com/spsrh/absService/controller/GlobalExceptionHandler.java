package com.spsrh.absService.controller;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
	public class GlobalExceptionHandler {

	    @ExceptionHandler(RuntimeException.class)
	    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
	        return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(ex.getMessage());
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<String> handleException(Exception ex) {
	        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + ex.getMessage());
	    }
	}



