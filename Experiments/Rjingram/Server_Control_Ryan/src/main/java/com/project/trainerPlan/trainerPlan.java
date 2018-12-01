package com.project.trainerPlan;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class trainerPlan {

	@Id
	@GeneratedValue 
	@Column(columnDefinition ="serial")
	private int entry;
	private int trainerId;
	private int userId;
	private String planName;
	private int day;
	private int exerciseId;
	private int sets;
	private int reps;
	
	public trainerPlan() {
		
	}
	
	public trainerPlan(int trainerId, int userId, String planName, int day, int exerciseId, int sets, int reps) {
		super();
		this.trainerId = trainerId;
		this.userId = userId;
		this.planName = planName;
		this.day = day;
		this.exerciseId = exerciseId;
		this.sets = sets;
		this.reps = reps;
	}
	public int getEntry() {
		return entry;
	}
	public void setEntry(int entry) {
		this.entry = entry;
	}
	public int getTrainerId() {
		return trainerId;
	}
	public void setTrainerId(int trainerId) {
		this.trainerId = trainerId;
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
}
