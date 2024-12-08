package com.spsrh.absService.dto;

import java.time.LocalDate;

import com.spsrh.absService.model.LeaveStatus;
import com.spsrh.absService.model.LeaveType;

public class LeaveRequestDTO {

	

	    private Long id;
	    private Long employeeId;
	    private String employeeName;
	    private LeaveType leaveType;
	    private LeaveStatus status;
	    private LocalDate startDate;
	    private LocalDate endDate;
	    private String reason;
	    private String managerComment;
	    
	 // Getters et Setters
	    
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Long getEmployeeId() {
			return employeeId;
		}
		public void setEmployeeId(Long employeeId) {
			this.employeeId = employeeId;
		}
		public String getEmployeeName() {
			return employeeName;
		}
		public void setEmployeeName(String employeeName) {
			this.employeeName = employeeName;
		}
		public LeaveType getLeaveType() {
			return leaveType;
		}
		public void setLeaveType(LeaveType leaveType) {
			this.leaveType = leaveType;
		}
		public LeaveStatus getStatus() {
			return status;
		}
		public void setStatus(LeaveStatus status) {
			this.status = status;
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

	   
	
	    

	
}
