package com.project.plan;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class plan {

	@Id
	@GeneratedValue 
	@Column(columnDefinition ="serial")
	private int entry;
	private int userId;
	private String planName;
	private int currentDay;
	private int maxDay;
	private boolean active;
	private String assignedBy;
	
	public plan() {
		
	}
	
	public plan(int userId, String planName, int currentDay, int maxDay, boolean active, String assignedBy) {
		super();
		this.userId = userId;
		this.planName = planName;
		this.currentDay = currentDay;
		this.maxDay = maxDay;
		this.active = active;
		this.assignedBy = assignedBy;
	}
	
	
	public int getEntry() {
		return entry;
	}

	public void setEntry(int entry) {
		this.entry = entry;
	}

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public int getCurrentDay() {
		return currentDay;
	}
	public void setCurrentDay(int currentDay) {
		this.currentDay = currentDay;
	}
	
	public int getMaxDay() {
		return maxDay;
	}

	public void setMaxDay(int maxDay) {
		this.maxDay = maxDay;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getAssignedBy() {
		return assignedBy;
	}

	public void setAssignedBy(String assignedBy) {
		this.assignedBy = assignedBy;
	}

	
	
}
