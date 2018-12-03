package com.project.dayExercise;

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
public class dayExercise {

	@Id
	@GeneratedValue 
	@Column(columnDefinition ="serial")
	private int entry;
	private int userId;
	private String planName;
	private int exerciseId;
	private int sets;
	private int reps;
	private boolean complete;
	
	public dayExercise() {
		
	}
	/**
	 * 
	 * @param userId
	 * @param planName
	 * @param exerciseId
	 * @param sets
	 * @param reps
	 * @param complete
	 */
	public dayExercise(int userId, String planName, int exerciseId, int sets, int reps, boolean complete) {
		super();
		this.userId = userId;
		this.planName = planName;
		this.exerciseId = exerciseId;
		this.sets = sets;
		this.reps = reps;
		this.complete = complete;
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
	/**
	 * 
	 * @return
	 */
	public boolean isComplete() {
		return complete;
	}
	/**
	 * 
	 * @param complete
	 */
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
}
