package com.project.userExercise;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class userExercise {

	@Id
	private int userId;
	private int date;
	private int exerciseId;
	private int sets;
	private int reps;
	private boolean complete;
	
	public userExercise() {
		
	}
	
	public userExercise(int userId, int date, int exerciseId, int sets, int reps, boolean complete) {
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
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public int getExercise() {
		return exerciseId;
	}
	public void setExercise(int exerciseId) {
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
