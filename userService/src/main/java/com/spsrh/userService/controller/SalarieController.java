package com.spsrh.userService.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import com.spsrh.userService.dto.AssignTeamDTO;
import com.spsrh.userService.dto.SalarieDTO;
import com.spsrh.userService.dto.UserCredentials;
import com.spsrh.userService.model.Salarie;
import com.spsrh.userService.service.SalarieService;

@RestController
@RequestMapping("/api/salaries")
public class SalarieController {

    private final SalarieService salarieService;

    public SalarieController(SalarieService salarieService) {
        this.salarieService = salarieService;
    }

    @PostMapping
    public  ResponseEntity<?>  createSalarie(@RequestBody SalarieDTO salarieDTO) {
        return salarieService.createSalarie(salarieDTO);
    }

    @GetMapping
    public List<Salarie> getAllSalaries() {
        return salarieService.getAllSalaries();
    }

    @GetMapping("/{id}")
    public Salarie getSalarieById(@PathVariable Long id) {
        return salarieService.getSalarieById(id);
    }
 // Update Salarie
    @PutMapping("/{id}")
    public ResponseEntity<Salarie> updateSalarie(@PathVariable Long id, @RequestBody SalarieDTO salarieDTO) {
        Salarie updatedSalarie = salarieService.updateSalarie(id, salarieDTO);
        return ResponseEntity.ok(updatedSalarie);
    }

    // Delete Salarie
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalarie(@PathVariable Long id) {
        salarieService.deleteSalarie(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/validate")
    public Boolean validateUser(@RequestBody UserCredentials credentials) {
        return salarieService.validateCredentials(credentials);
    }
    
    
    
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
