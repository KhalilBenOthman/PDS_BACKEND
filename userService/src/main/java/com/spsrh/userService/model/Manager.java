package com.spsrh.userService.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "managers")
public class Manager {

    @Id
    private Long id; // Same as User.id

    private String teamName;

    @OneToMany(mappedBy = "manager")
    private List<Salarie> teamMembers; // Team members managed by this manager.

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

	public List<Salarie> getTeamMembers() {
		return teamMembers;
	}

	public void setTeamMembers(List<Salarie> teamMembers) {
		this.teamMembers = teamMembers;
	}

    
    
}

