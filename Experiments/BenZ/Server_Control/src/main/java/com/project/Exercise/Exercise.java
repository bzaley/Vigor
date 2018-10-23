package com.project.Exercise;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Exercise {

	@Id
	private int exerciseId;
	private String exercise;
	
	public Exercise() {
		
	}
	
	public Exercise(int exerciseId, String exercise) {
		super();
		this.exerciseId = exerciseId;
		this.exercise = exercise;
	}
	
	
	public int getExerciseId() {
		return exerciseId;
	}
	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}
	public String getExercise() {
		return exercise;
	}
	public void setExercise(String exercise) {
		this.exercise = exercise;
	}
	
	
}
