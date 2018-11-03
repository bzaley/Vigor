package com.project.userExercise;

public class userEntry {

	
	private int userId;
	private String date;
	private String exercise;
	private int sets;
	private int reps;
	private boolean complete;
	
	
	public userEntry() {
		
	}
	
	public userEntry(int userId, String date, String exercise, int sets, int reps, boolean complete) {
		super();
		this.userId = userId;
		this.date = date;
		this.exercise = exercise;
		this.sets = sets;
		this.reps = reps;
		this.complete = complete;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
	public boolean isComplete() {
		return complete;
	}
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	
}
