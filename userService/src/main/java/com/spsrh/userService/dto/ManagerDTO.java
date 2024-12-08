package com.spsrh.userService.dto;

import java.util.List;

public class ManagerDTO {
    private Long id;
    private String teamName;
    private List<Long> teamMemberIds; // Only IDs of the team members

    // Getters and Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public List<Long> getTeamMemberIds() {
		return teamMemberIds;
	}
	public void setTeamMemberIds(List<Long> teamMemberIds) {
		this.teamMemberIds = teamMemberIds;
	}

    
}
