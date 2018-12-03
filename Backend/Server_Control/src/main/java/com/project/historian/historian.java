package com.project.historian;

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
public class historian {

	
	@Id
	@GeneratedValue 
	@Column(columnDefinition ="serial")
	private int entry;
	private int userId;
	private int exerciseId;
	private int sets;
	private int reps;
	private String saveDate;
	
	public historian() {
		
	}
	/**
	 * Table constructor for historian table
	 * @param userId
	 * @param exerciseId
	 * @param sets
	 * @param reps
	 * @param saveDate
	 */
	public historian(int userId, int exerciseId, int sets, int reps, String saveDate) {
		super();
		this.userId = userId;
		this.exerciseId = exerciseId;
		this.sets = sets;
		this.reps = reps;
		this.saveDate = saveDate;
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
	public String getSaveDate() {
		return saveDate;
	}
	/**
	 * 
	 * @param saveDate
	 */
	public void setSaveDate(String saveDate) {
		this.saveDate = saveDate;
	}
	
	
	
}
