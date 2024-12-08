package com.spsrh.absService.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class AuditLog {
	
	
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "leave_request_id", nullable = false)
	    private LeaveRequest leaveRequest;

	    @Column(nullable = false)
	    private String action; // Exemple: "CREATED", "APPROVED", "REJECTED"

	    @Column(nullable = false)
	    private String performedBy; // Manager ou système

	    @Column(nullable = false)
	    private LocalDateTime timestamp = LocalDateTime.now();

	    private String details;
	    
	    // Getters et Setters

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public LeaveRequest getLeaveRequest() {
			return leaveRequest;
		}

		public void setLeaveRequest(LeaveRequest leaveRequest) {
			this.leaveRequest = leaveRequest;
		}

		public String getAction() {
			return action;
		}

		public void setAction(String action) {
			this.action = action;
		}

		public String getPerformedBy() {
			return performedBy;
		}

		public void setPerformedBy(String performedBy) {
			this.performedBy = performedBy;
		}

		public LocalDateTime getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
		}

		public String getDetails() {
			return details;
		}

		public void setDetails(String details) {
			this.details = details;
		}

	  
	    
	    
	    
	}



