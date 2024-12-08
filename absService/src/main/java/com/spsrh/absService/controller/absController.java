package com.spsrh.absService.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class absController {
	  @GetMapping("health-check")
	  public String sayHello() {

	    return "absService - Everythings is okey";
	  }

}
