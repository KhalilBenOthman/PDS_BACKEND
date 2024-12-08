package com.spsrh.userService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spsrh.userService.model.Manager;


public interface ManagerRepository extends JpaRepository<Manager, Long> {
	Manager findManagerById(Long id); // Custom query method
	List<Manager> findByTeamName(String teamName);
	

}
