package com.spsrh.userService.repository;

import com.spsrh.userService.model.ManagerTeam;
import com.spsrh.userService.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerTeamRepository extends JpaRepository<ManagerTeam, Long> {

	Optional<ManagerTeam> findByManager(User manager);

    // Custom queries can be added here as needed
}
