package com.spsrh.authService.feign;

import com.spsrh.authService.dto.UserCredentials;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "userService", path = "/api/users")
public interface UserServiceFeignClient {

    @PostMapping("/validate")
    Boolean validateUser(@RequestBody UserCredentials credentials);
}
