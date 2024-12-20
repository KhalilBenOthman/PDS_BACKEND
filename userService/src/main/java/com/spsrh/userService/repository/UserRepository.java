package com.spsrh.userService.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spsrh.userService.model.Employee;
import com.spsrh.userService.model.Manager;
import com.spsrh.userService.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

	User findByUsername(String managerUsername);
    Optional<User> findById(Long id); // Find user by ID
    List<User> findAll(); // Get all users

	Collection<Employee> findAllByUsernameIn(List<String> teamMemberUsernames);
}