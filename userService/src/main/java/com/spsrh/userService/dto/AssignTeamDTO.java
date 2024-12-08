package com.spsrh.userService.dto;

import java.util.List;

public class AssignTeamDTO {
    private Long managerId;
    private List<Long> teamMemberIds;
    
    // Getters and Setters
	public Long getManagerId() {
		return managerId;
	}
	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}
	public List<Long> getTeamMemberIds() {
		return teamMemberIds;
	}
	public void setTeamMemberIds(List<Long> teamMemberIds) {
		this.teamMemberIds = teamMemberIds;
	}


    
    
}
