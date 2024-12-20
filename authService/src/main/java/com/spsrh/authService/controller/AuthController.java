package com.spsrh.authService.controller;

import com.spsrh.authService.dto.UserCredentials;
import com.spsrh.authService.dto.JwtResponse;
import com.spsrh.authService.service.AuthService;
import com.spsrh.authService.util.JwtUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*") // Allow all origins for this controller
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody UserCredentials credentials) {
        String token = authService.login(credentials);
        return ResponseEntity.ok(new JwtResponse(token));
    }
    
    @GetMapping("/user-from-token")
    public ResponseEntity<String> getUserFromToken(@RequestHeader("Authorization") String token) {
    	token = token.startsWith("Bearer ") ? token.substring(7) : token;
    	String username="";
		try {
			username = JwtUtil.extractUsernameFromToken(token);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return ResponseEntity.ok(username);
    }
}
