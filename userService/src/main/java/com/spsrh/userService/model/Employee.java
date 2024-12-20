package com.spsrh.userService.model;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("EMPLOYEE") // This will be used to differentiate Employee type
@Data
@EqualsAndHashCode(callSuper = true) // For proper inheritance behavior
public class Employee extends User {
    

	@Column(name = "hire_date")
    private LocalDateTime hireDate; // Example: use LocalDate for proper date handling

    @Column(nullable = false)
    private String jobTitle;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Department department;
    @Column
    private String skills; // JSON or separate skill table can be used    
    private String salary; 
    @Column(nullable = false)
    private String availabilityStatus;    
    @Column(name = "last_performance_review_date")
    private LocalDateTime lastPerformanceReviewDate;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;    
    
    
    
    
    @ManyToOne
    @JoinColumn(name = "manager_id") // Foreign key to the manager's ID
    @JsonBackReference
    private Manager manager; // The manager of this employee
    
    


	public LocalDateTime getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDateTime hireDate) {
		this.hireDate = hireDate;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getAvailabilityStatus() {
		return availabilityStatus;
	}

	public void setAvailabilityStatus(String availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
	}

	public LocalDateTime getLastPerformanceReviewDate() {
		return lastPerformanceReviewDate;
	}

	public void setLastPerformanceReviewDate(LocalDateTime lastPerformanceReviewDate) {
		this.lastPerformanceReviewDate = lastPerformanceReviewDate;
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
	@PrePersist
	public void prePersist() {
	    this.createdAt = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
	    this.updatedAt = LocalDateTime.now();
	} 


}
