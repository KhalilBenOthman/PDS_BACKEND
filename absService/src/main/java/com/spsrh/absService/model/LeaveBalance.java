package com.spsrh.absService.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class LeaveBalance {
	

	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @OneToOne
	    @JoinColumn(name = "employee_id", nullable = false)
	    private Employee employee;

	    @Column(nullable = false)
	    private int totalLeaves;

	    @Column(nullable = false)
	    private int usedLeaves;

	    @Column(nullable = false)
	    private int remainingLeaves;
	    
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

		public int getTotalLeaves() {
			return totalLeaves;
		}

		public void setTotalLeaves(int totalLeaves) {
			this.totalLeaves = totalLeaves;
		}

		public int getUsedLeaves() {
			return usedLeaves;
		}

		public void setUsedLeaves(int usedLeaves) {
			this.usedLeaves = usedLeaves;
		}

		public int getRemainingLeaves() {
			return remainingLeaves;
		}

		public void setRemainingLeaves(int remainingLeaves) {
			this.remainingLeaves = remainingLeaves;
		}

	 
	    
	    
	}



