package com.spsrh.absService.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity

public class LeaveRequest {
	
	

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "employee_id", nullable = false)
	    private Employee employee;

	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false)
	    private LeaveType leaveType; // ENUM: VACATION, SICK_LEAVE, etc.

	    @Column(nullable = false)
	    private LocalDate startDate;

	    @Column(nullable = false)
	    private LocalDate endDate;

	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false)
	    private LeaveStatus status; // ENUM: PENDING, APPROVED, REJECTED

	    private String reason;

	    private String managerComment;

	    @Column(nullable = false)
	    private LocalDate requestDate = LocalDate.now();

	    // Getters et Setters
	    
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Employee getEmployee() {
			return employee;
		}

		public void setEmployee(Employee employee) {
			this.employee = employee;
		}

		public LeaveType getLeaveType() {
			return leaveType;
		}

		public void setLeaveType(LeaveType leaveType) {
			this.leaveType = leaveType;
		}

		public LocalDate getStartDate() {
			return startDate;
		}

		public void setStartDate(LocalDate startDate) {
			this.startDate = startDate;
		}

		public LocalDate getEndDate() {
			return endDate;
		}

		public void setEndDate(LocalDate endDate) {
			this.endDate = endDate;
		}

		public LeaveStatus getStatus() {
			return status;
		}

		public void setStatus(LeaveStatus status) {
			this.status = status;
		}

		public String getReason() {
			return reason;
		}

		public void setReason(String reason) {
			this.reason = reason;
		}

		public String getManagerComment() {
			return managerComment;
		}

		public void setManagerComment(String managerComment) {
			this.managerComment = managerComment;
		}

		public LocalDate getRequestDate() {
			return requestDate;
		}

		public void setRequestDate(LocalDate requestDate) {
			this.requestDate = requestDate;
		}

	   
	}


