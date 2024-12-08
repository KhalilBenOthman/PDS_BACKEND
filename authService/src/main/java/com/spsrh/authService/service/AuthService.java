package com.spsrh.authService.service;

import com.spsrh.authService.dto.UserCredentials;
import com.spsrh.authService.feign.UserServiceFeignClient;
import com.spsrh.authService.util.JwtUtil;
import org.springframework.stereotype.Service;
import com.spsrh.authService.exception.BadCredentialsException;

@Service
public class AuthService {

    private final UserServiceFeignClient userServiceFeignClient;
    private final JwtUtil jwtUtil;

    public AuthService(UserServiceFeignClient userServiceFeignClient, JwtUtil jwtUtil) {
        this.userServiceFeignClient = userServiceFeignClient;
        this.jwtUtil = jwtUtil;
    }

    public String login(UserCredentials credentials) {
        Boolean isValid = userServiceFeignClient.validateUser(credentials);

        if (Boolean.TRUE.equals(isValid)) {
            return jwtUtil.generateToken(credentials.getUsername());
        } else {
        	throw new BadCredentialsException("Invalid email or password");
        }
    }
}
