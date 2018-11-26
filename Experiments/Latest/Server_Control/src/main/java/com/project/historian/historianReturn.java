package com.project.historian;

public class historianReturn {

	private String exercise;
	private int sets;
	private int reps;
	
	public historianReturn() {
		
	}
	
	public historianReturn(String exercise, int sets, int reps) {
		super();
		this.exercise = exercise;
		this.sets = sets;
		this.reps = reps;
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
