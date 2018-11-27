package com.project.plan;

public class planReturn {

	
	private String planName;
	private boolean active;
	
	public planReturn() {
		
	}
	
	public planReturn(String planName, boolean active) {
		super();
		this.planName = planName;
		this.active = active;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	
}
