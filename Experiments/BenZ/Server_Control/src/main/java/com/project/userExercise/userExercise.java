package com.project.userExercise;

import javax.persistence.Entity;
import javax.persistence.Id;

// Finalized Ryan Ingram

@Entity
public class userExercise {

	@Id
	private int userId;
	private String planName;
	private int day;
	private int exerciseId;
	private int sets;
	private int reps;
	private String saveDate;
	
	
	public userExercise() {
		
	}
	
	public userExercise(int userId, String planName, int day, int exerciseId, int sets, int reps, String saveDate) {
		super();
		this.userId = userId;
		this.planName = planName;
		this.day = day;
		this.exerciseId = exerciseId;
		this.sets = sets;
		this.reps = reps;
		this.saveDate = saveDate;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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
	public String getSaveDate() {
		return saveDate;
	}
	public void setSaveDate(String saveDate) {
		this.saveDate = saveDate;
	}
	
	
	
}
