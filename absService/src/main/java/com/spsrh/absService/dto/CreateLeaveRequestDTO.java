package com.spsrh.absService.dto;

import java.time.LocalDate;

import com.spsrh.absService.model.LeaveType;

public class CreateLeaveRequestDTO {
	
	
	    private Long employeeId;
	    private LeaveType leaveType;
	    private LocalDate startDate;
	    private LocalDate endDate;
	    private String reason;
	    
	    // Getters et Setters
	    
		public Long getEmployeeId() {
			return employeeId;
		}
		public void setEmployeeId(Long employeeId) {
			this.employeeId = employeeId;
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
		public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
		}


	

}
