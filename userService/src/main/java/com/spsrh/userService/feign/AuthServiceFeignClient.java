package com.spsrh.userService.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "authService", path = "/api/auth")
public interface AuthServiceFeignClient {
	@GetMapping("/user-from-token")
    String getUserFromToken(@RequestHeader("Authorization") String token);
}
