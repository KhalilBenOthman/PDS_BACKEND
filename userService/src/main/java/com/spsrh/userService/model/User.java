package com.spsrh.userService.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Role role; // e.g., Employee, Manager, Admin

    @Column(nullable = false)
    private Boolean isActive;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    private String Adresse;
    
    private String city;
   
    private String codePostal;
    
    private String pays;
    
    private String phoneNumber;
    
    @ManyToOne
    @JoinColumn(name = "team_id")
    @JsonBackReference
    private ManagerTeam team; // Relation pour lier l'utilisateur à une équipe

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Allow serialization of Employee without infinite loop
    private Employee employee;
    
    
    // Getters and setters
    
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getAdresse() {
		return Adresse;
	}

	public void setAdresse(String adresse) {
		Adresse = adresse;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
	
	public ManagerTeam getTeam() {
		return team;
	}

	public void setTeam(ManagerTeam team) {
		this.team = team;
	}

	@PrePersist
	public void prePersist() {
	    this.createdAt = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
	    this.updatedAt = LocalDateTime.now();
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}  
    
}
