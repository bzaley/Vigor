package com.project.trainerPlan;
/**
 * 
 * @author Ryan Ingram
 *
 */
public class trainerAdd {

	
	private int trainerId;
	private String userEmail;
	private String planName;
	private int day;
	private String exercise;
	private int sets;
	private int reps;
	
	public trainerAdd() {
		
	}
	/**
	 * constructor for creating a personal trainer user.
	 * @param trainerId
	 * @param userEmail
	 * @param planName
	 * @param day
	 * @param exercise
	 * @param sets
	 * @param reps
	 */
	public trainerAdd(int trainerId, String userEmail, String planName, int day, String exercise, int sets, int reps) {
		super();
		this.trainerId = trainerId;
		this.userEmail = userEmail;
		this.planName = planName;
		this.day = day;
		this.exercise = exercise;
		this.sets = sets;
		this.reps = reps;
	}
	/**
	 * 
	 * @return
	 */
	public int getTrainerId() {
		return trainerId;
	}
	/**
	 * 
	 * @param trainerId
	 */
	public void setTrainerId(int trainerId) {
		this.trainerId = trainerId;
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
