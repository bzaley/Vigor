package com.project.trainerExercise;

public class trainerEntry {

	
	private int trainerId;
	private String email;
	private String planName;
	private String exercise;
	private int sets;
	private int reps;
	
	public trainerEntry() {
		
	}
	
	
	public trainerEntry(int trainerId, String email, String planName, String exercise, int sets, int reps) {
		super();
		this.trainerId = trainerId;
		this.email = email;
		this.planName = planName;
		this.exercise = exercise;
		this.sets = sets;
		this.reps = reps;
	}
	public int getTrainerId() {
		return trainerId;
	}
	public void setTrainerId(int trainerId) {
		this.trainerId = trainerId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
