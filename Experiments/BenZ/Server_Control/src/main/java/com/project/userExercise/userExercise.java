package com.project.userExercise;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class userExercise {

	@Id
	private int userId;
	private int date;
	private String exercise;
	private int sets;
	private int reps;
	private boolean complete;
	
	public userExercise() {
		
	}
	
	public userExercise(int userId, int date, String exercise, int sets, int reps, boolean complete) {
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
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
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
