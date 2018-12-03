package com.project.plan;
/**
 * 
 * @author Ryan Ingram
 *
 */
public class planReturn {

	
	private String planName;
	private boolean active;
	
	public planReturn() {
		
	}
	/**
	 * JSON return constructor
	 * @param planName
	 * @param active
	 */
	public planReturn(String planName, boolean active) {
		super();
		this.planName = planName;
		this.active = active;
	}
	/**
	 * 
	 * @return
	 */
	public String getPlanName() {
		return planName;
	}
	/**
	 * 
	 * @param planName
	 */
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	/**
	 * 
	 * @return
	 */
	public boolean isActive() {
		return active;
	}
	/**
	 * 
	 * @param active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	
}
