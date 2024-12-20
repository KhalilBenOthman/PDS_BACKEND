package com.spsrh.absService.model;

import java.util.List;



import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("MANAGER")  // This differentiates the Manager role from Employee
@Data
@EqualsAndHashCode(callSuper = true)
public class Manager extends Employee {
	@OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<Employee> team; // Team of employees managed by this manager
	
    public List<Employee> getTeam() {
		return team;
	}

	public void setTeam(List<Employee> team) {
		this.team = team;
	}


}
