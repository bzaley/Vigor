package com.project.dayExercise;

public class dayExerciseComp {

	
	private String userEmail;
	private String planName;
	private String exercise;
	private int sets;
	private int reps;
	
	public dayExerciseComp() {
		
	}
	
	public dayExerciseComp(String userEmail, String planName, String exercise, int sets, int reps) {
		super();
		this.userEmail = userEmail;
		this.planName = planName;
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
