package com.spsrh.userService.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.spsrh.userService.dto.AssignTeamDTO;
import com.spsrh.userService.dto.SalarieDTO;
import com.spsrh.userService.dto.UserCredentials;
import com.spsrh.userService.model.Salarie;

public interface SalarieService {
    ResponseEntity<?> createSalarie(SalarieDTO salarieDTO);
    List<Salarie> getAllSalaries();
    Salarie assignTeam(AssignTeamDTO assignTeamDTO);
    Salarie getSalarieById(Long id);
    boolean validateCredentials(UserCredentials credentials);
    Salarie updateSalarie(Long id, SalarieDTO salarieDTO);
    void deleteSalarie(Long id);
}