package com.project.userPlan;

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
public class userPlan {

	@Id
	@GeneratedValue 
	@Column(columnDefinition ="serial")
	private int entry;
	private int userId;
	private String planName;
	private int day;
	private int exerciseId;
	private int sets;
	private int reps;
	
	public userPlan( ) {
		
	}
	/**
	 * Constructor for userPlan table and object.
	 * @param userId
	 * @param planName
	 * @param day
	 * @param exerciseId
	 * @param sets
	 * @param reps
	 */
	public userPlan(int userId, String planName, int day, int exerciseId, int sets, int reps) {
		super();
		this.userId = userId;
		this.planName = planName;
		this.day = day;
		this.exerciseId = exerciseId;
		this.sets = sets;
		this.reps = reps;
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
	public int getDay() {
		return day;
	}
	/**
	 * 
	 * @param day
	 */
	public void setDay(int day) {
		this.day = day;
	}
	/**
	 * 
	 * @return
	 */
	public int getExerciseId() {
		return exerciseId;
	}
	/**
	 * 
	 * @param exerciseId
	 */
	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}
	/**
	 * 
	 * @return
	 */
	public int getSets() {
		return sets;
	}
	/**
	 * 
	 * @param sets
	 */
	public void setSets(int sets) {
		this.sets = sets;
	}
	/**
	 * 
	 * @return
	 */
	public int getReps() {
		return reps;
	}
	/**
	 * 
	 * @param reps
	 */
	public void setReps(int reps) {
		this.reps = reps;
	}
}
