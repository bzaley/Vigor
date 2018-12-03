package com.project.dayExercise;
/**
 * 
 * @author Ryan Ingram
 *
 */
public class dayExerciseAdd {

	
	private String userEmail;
	private String exercise;
	private int sets;
	private int reps;
	
	public dayExerciseAdd() {
		
	}
	/**
	 * Form for adding an exercise.
	 * @param userEmail
	 * @param exercise
	 * @param sets
	 * @param reps
	 */
	public dayExerciseAdd(String userEmail, String exercise, int sets, int reps) {
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
