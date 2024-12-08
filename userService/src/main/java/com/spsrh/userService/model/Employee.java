package com.spsrh.userService.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    @JsonBackReference
    private User manager;

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

    @Column(name = "hire_date")
    private LocalDateTime hireDate;

    @Column(name = "last_performance_review_date")
    private LocalDateTime lastPerformanceReviewDate;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    // Getters and setters
    
	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
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

	public LocalDateTime getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDateTime hireDate) {
		this.hireDate = hireDate;
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
