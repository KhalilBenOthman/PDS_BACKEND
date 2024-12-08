package com.spsrh.absService.dto;

import com.spsrh.absService.model.LeaveStatus;

public class UpdateLeaveStatusDTO {
	

	    private Long leaveRequestId;
	    private LeaveStatus status;
	    private String managerComment;
	    
	    // Getters et Setters
	    
		public Long getLeaveRequestId() {
			return leaveRequestId;
		}
		public void setLeaveRequestId(Long leaveRequestId) {
			this.leaveRequestId = leaveRequestId;
		}
		public LeaveStatus getStatus() {
			return status;
		}
		public void setStatus(LeaveStatus status) {
			this.status = status;
		}
		public String getManagerComment() {
			return managerComment;
		}
		public void setManagerComment(String managerComment) {
			this.managerComment = managerComment;
		}

	  
	    
	    
	}



