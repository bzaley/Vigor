package com.project.dayExercise;
/**
 * 
 * @author Ryan Ingram
 *
 */
public class dayExerciseRemove {

	
	private String userEmail;
	private String exercise;
	private int sets;
	private int reps;
	
	public dayExerciseRemove() {
		
	}
	/**
	 * Form for submitting delete JSON
	 * @param userEmail
	 * @param exercise
	 * @param sets
	 * @param reps
	 */
	public dayExerciseRemove(String userEmail, String exercise, int sets, int reps) {
		super();
		this.userEmail = userEmail;
		this.exercise = exercise;
		this.sets = sets;
		this.reps = reps;
	}
	/**
	 * 
	 * @return
	 */
	public String getUserEmail() {
		return userEmail;
	}
	/**
	 * 
	 * @param userEmail
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	/**
	 * 
	 * @return
	 */
	public String getExercise() {
		return exercise;
	}
	/**
	 * 
	 * @param exercise
	 */
	public void setExercise(String exercise) {
		this.exercise = exercise;
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
