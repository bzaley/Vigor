package com.project.plan;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/**
 * 
 * @author Ryan Ingram
 *
 */
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
	/**
	 * Table constructor for exercise plans.
	 * @param userId
	 * @param planName
	 * @param currentDay
	 * @param maxDay
	 * @param active
	 * @param assignedBy
	 */
	public plan(int userId, String planName, int currentDay, int maxDay, boolean active, String assignedBy) {
		super();
		this.userId = userId;
		this.planName = planName;
		this.currentDay = currentDay;
		this.maxDay = maxDay;
		this.active = active;
		this.assignedBy = assignedBy;
	}

	/**
	 * 
	 * @return
	 */
	public int getEntry() {
		return entry;
	}
	/**
	 * 
	 * @param entry
	 */
	public void setEntry(int entry) {
		this.entry = entry;
	}
	/**
	 * 
	 * @return
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * 
	 * @param userId
	 */
	public void setUserId(int userId) {
		this.userId = userId;
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
	public int getCurrentDay() {
		return currentDay;
	}
	/**
	 * 
	 * @param currentDay
	 */
	public void setCurrentDay(int currentDay) {
		this.currentDay = currentDay;
	}
	/**
	 * 
	 * @return
	 */
	public int getMaxDay() {
		return maxDay;
	}
	/**
	 * 
	 * @param maxDay
	 */
	public void setMaxDay(int maxDay) {
		this.maxDay = maxDay;
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
	/**
	 * 
	 * @return
	 */
	public String getAssignedBy() {
		return assignedBy;
	}
	/**
	 * 
	 * @param assignedBy
	 */
	public void setAssignedBy(String assignedBy) {
		this.assignedBy = assignedBy;
	}



}
