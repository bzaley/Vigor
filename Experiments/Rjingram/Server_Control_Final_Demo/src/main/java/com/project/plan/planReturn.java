package com.project.plan;

public class planReturn {

	
	private String planName;
	private int currentDay;
	private boolean active;
	
	public planReturn() {
		
	}
	
	public planReturn(String planName, int currentDay, boolean active) {
		super();
		this.planName = planName;
		this.currentDay = currentDay;
		this.active = active;
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
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	
}
