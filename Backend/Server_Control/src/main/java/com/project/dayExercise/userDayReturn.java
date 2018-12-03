package com.project.dayExercise;
/**
 * 
 * @author Ryan Ingram
 *
 */
public class userDayReturn {

	
	
	private String userEmail;
	private String planName;
	private String exercise;
	private int sets;
	private int reps;
	
	public userDayReturn() {
		
	}

	
	
	/**
	 * JSON format for returning an exercise in a plan.
	 * @param userEmail
	 * @param planName
	 * @param exercise
	 * @param sets
	 * @param reps
	 */
	public userDayReturn(String userEmail, String planName, String exercise, int sets, int reps) {
		super();
		this.userEmail = userEmail;
		this.planName = planName;
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
