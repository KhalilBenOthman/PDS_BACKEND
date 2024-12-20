package com.spsrh.userService.dto;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ManagerDTO extends EmployeeDTO {
    private List<String> teamMemberUsernames; // List of usernames of employees in the manager's team

	public List<String> getTeamMemberUsernames() {
		return teamMemberUsernames;
	}
	public void setTeamMemberUsernames(List<String> teamMemberUsernames) {
		this.teamMemberUsernames = teamMemberUsernames;
	}
    
    
}
