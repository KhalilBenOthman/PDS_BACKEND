package com.spsrh.authService.dto;


import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EmployeeDTO extends UserDTO {
    private String jobTitle;
    private String department; // Enum value as a String
    private String skills;
    private String salary;
    private String availabilityStatus;
    private LocalDateTime hireDate;
    private LocalDateTime lastPerformanceReviewDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String managerUsername; // Username of the superior manager
    

	public String getManagerUsername() {
		return managerUsername;
	}
	public void setManagerUsername(String managerSuperieurUsername) {
		this.managerUsername = managerSuperieurUsername;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
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
    
    
}
