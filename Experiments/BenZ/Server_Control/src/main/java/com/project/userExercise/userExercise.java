package com.project.userExercise;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class userExercise {

	@Id
	private int userId;
	private String date;
	private int exerciseId;
	private int sets;
	private int reps;
	private boolean complete;
	
	public userExercise() {
		
	}
	
	public userExercise(int userId, String date, int exerciseId, int sets, int reps, boolean complete) {
		super();
		this.userId = userId;
		this.date = date;
		this.exerciseId = exerciseId;
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
	public int getExerciseId() {
		return exerciseId;
	}
	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
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
