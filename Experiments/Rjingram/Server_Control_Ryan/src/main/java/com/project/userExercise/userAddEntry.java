package com.project.userExercise;

// Finalized Ryan Ingram


// Object that will be excepted for any add to userExercise table
// Difference with table is exercise to exerciseId

public class userAddEntry {

	private int userId;
	private String planName;
	private int day;
	private String exercise;
	private int sets;
	private int reps;
	
	public userAddEntry() {
		
	}
	
	public userAddEntry(int userId, String planName, int day, String exercise, int sets, int reps) {
		super();
		this.userId = userId;
		this.planName = planName;
		this.day = day;
		this.exercise = exercise;
		this.sets = sets;
		this.reps = reps;
	}
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public String getExercise() {
		return exercise;
	}
	public void setExercise(String exercise) {
		this.exercise = exercise;
	}
	public int getSets() {
		return sets;
	}
	public void setSets(int sets) {
		this.sets = sets;
	}
	public int getReps() {
		return reps;
	}
	public void setReps(int reps) {
		this.reps = reps;
	}
}
