package com.spsrh.authService.controller;

import com.spsrh.authService.dto.UserCredentials;
import com.spsrh.authService.dto.JwtResponse;
import com.spsrh.authService.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
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
}
