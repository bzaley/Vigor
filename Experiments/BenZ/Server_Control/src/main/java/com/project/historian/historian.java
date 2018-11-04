package com.project.historian;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class historian {

	@Id
	@GeneratedValue
	@Column(columnDefinition = "serial")
	private int entry;
	private int userId;
	private int exerciseId;
	private int sets;
	private int reps;
	private String saveDate;
	
	public historian() {
		
	}
	
	public historian(int entry, int userId, int exerciseId, int sets, int reps, String saveDate) {
		super();
		this.entry = entry;
		this.userId = userId;
		this.exerciseId = exerciseId;
		this.sets = sets;
		this.reps = reps;
		this.saveDate = saveDate;
	}
	public int getEntry() {
		return entry;
	}
	public void setEntry(int entry) {
		this.entry = entry;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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
