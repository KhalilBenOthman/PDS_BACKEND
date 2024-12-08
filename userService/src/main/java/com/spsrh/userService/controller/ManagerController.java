package com.spsrh.userService.controller;

import com.spsrh.userService.dto.ManagerDTO;
import com.spsrh.userService.model.Manager;
import com.spsrh.userService.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/managers")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    // Create Manager
    @PostMapping
    public ResponseEntity<Manager> createManager(@RequestBody ManagerDTO managerDTO) {
        Manager manager = managerService.createManager(managerDTO);
        return ResponseEntity.ok(manager);
    }

    // Get all Managers
    @GetMapping
    public ResponseEntity<List<Manager>> getAllManagers() {
        return ResponseEntity.ok(managerService.getAllManagers());
    }

    // Get Manager by ID
    @GetMapping("/{id}")
    public ResponseEntity<Manager> getManagerById(@PathVariable Long id) {
        return ResponseEntity.ok(managerService.getManagerById(id));
    }

    // Update Manager
    @PutMapping("/{id}")
    public ResponseEntity<Manager> updateManager(@PathVariable Long id, @RequestBody ManagerDTO managerDTO) {
        Manager updatedManager = managerService.updateManager(id, managerDTO);
        return ResponseEntity.ok(updatedManager);
    }

    // Delete Manager
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManager(@PathVariable Long id) {
        managerService.deleteManager(id);
        return ResponseEntity.noContent().build();
    }
}
