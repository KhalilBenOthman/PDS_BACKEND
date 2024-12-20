package com.spsrh.userService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spsrh.userService.model.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

	Manager findByUsername(String managerUsername);

}
