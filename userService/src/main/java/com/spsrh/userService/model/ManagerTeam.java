package com.spsrh.userService.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "manager_teams")
public class ManagerTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long teamId;

    @OneToOne
    @JoinColumn(name = "manager_id", nullable = false, unique = true)
    @JsonManagedReference 
    private User manager;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference 
    private List<User> employees; // Liste des employés dans l'équipe

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Getters and setters
	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<User> getEmployees() {
		return employees;
	}

	public void setEmployees(List<User> employees) {
		this.employees = employees;
	}
	public void addEmployee(User employee) {
        if (employee != null && !employees.contains(employee)) {
            employees.add(employee);
            employee.setTeam(this); // Ensure the relationship is bidirectional
        }
    }

    public void removeEmployee(User employee) {
        if (employee != null && employees.contains(employee)) {
            employees.remove(employee);
            employee.setTeam(null); // Break the relationship
        }
    }
	@PrePersist
	public void prePersist() {
	    this.createdAt = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
	    this.updatedAt = LocalDateTime.now();
	}     
     
}
