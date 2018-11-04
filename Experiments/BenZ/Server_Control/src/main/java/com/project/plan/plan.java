package com.project.plan;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class plan {

	@Id
	private int userId;
	private String planName;
	private int currentDay;
	
	public plan() {
		
	}
	
	public plan(int userId, String planName, int currentDay) {
		super();
		this.userId = userId;
		this.planName = planName;
		this.currentDay = currentDay;
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
	
	
	
	
}
