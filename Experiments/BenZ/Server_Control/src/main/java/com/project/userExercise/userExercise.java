package com.project.userExercise;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class userExercise {

	@Id
	private int userId;
	private int day;
	private int exerciseId;
	private int sets;
	private int reps;
	private boolean complete;
	
	public userExercise() {
		
	}
	
	public userExercise(int userId, int day, int exerciseId, int sets, int reps, boolean complete) {
		super();
		this.userId = userId;
		this.day = day;
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
		return day;
	}
	public void setDate(int day) {
		this.day = day;
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
