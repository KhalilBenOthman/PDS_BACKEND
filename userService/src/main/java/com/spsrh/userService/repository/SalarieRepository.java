package com.spsrh.userService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spsrh.userService.model.Role;
import com.spsrh.userService.model.Salarie;

import java.util.List;

public interface SalarieRepository extends JpaRepository<Salarie, Long> {
    List<Salarie> findByRole(Role role);
    List<Salarie> findByManager(Salarie manager);
    Salarie findByEmail(String email);
    Salarie findByUserName(String userName);
    
}
