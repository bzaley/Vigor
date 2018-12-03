package com.project.historian;
/**
 * 
 * @author Ryan Ingram
 *
 */
public class historianReturn {

	private String exercise;
	private int sets;
	private int reps;
	
	public historianReturn() {
		
	}
	/**
	 * JSON format for returning data to the client.
	 * @param exercise
	 * @param sets
	 * @param reps
	 */
	public historianReturn(String exercise, int sets, int reps) {
		super();
		this.exercise = exercise;
		this.sets = sets;
		this.reps = reps;
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
