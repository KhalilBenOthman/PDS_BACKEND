package com.spsrh.absService.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.spsrh.absService.dto.EmployeeDTO;
import com.spsrh.absService.model.User;


@FeignClient(name = "userService", path = "/api/employes")
public interface EmployeFeignClient {

	 
	 @GetMapping("/{username}")
	 ResponseEntity<EmployeeDTO> getEmploye(@PathVariable String username);

	 @GetMapping
	  List<EmployeeDTO> getAllEmployes();
}
