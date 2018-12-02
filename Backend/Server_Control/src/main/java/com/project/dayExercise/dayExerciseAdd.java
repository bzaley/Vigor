package com.project.dayExercise;

public class dayExerciseAdd {

	
	private String userEmail;
	private String exercise;
	private int sets;
	private int reps;
	
	public dayExerciseAdd() {
		
	}
	
	public dayExerciseAdd(String userEmail, String exercise, int sets, int reps) {
		super();
		this.userEmail = userEmail;
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
