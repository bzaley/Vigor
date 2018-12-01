package com.project.userPlan;

public class userAdd {

	
	private String userEmail;
	private String planName;
	private int day;
	private String exercise;
	private int sets;
	private int reps;
	
	public userAdd() {
		
	}
	
	public userAdd(String userEmail, String planName, int day, String exercise, int sets, int reps) {
		super();
		this.userEmail = userEmail;
		this.planName = planName;
		this.day = day;
		this.exercise = exercise;
		this.sets = sets;
		this.reps = reps;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
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
