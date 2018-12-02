package com.project.Exercise;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Exercise {

	@Id
	@GeneratedValue 
	@Column(columnDefinition ="serial")
	private int exerciseId;
	private String name;
	
	public Exercise() {
		
	}

	public Exercise(int exerciseId, String name) {
		super();
		this.exerciseId = exerciseId;
		this.name = name;
	}

	public int getExerciseId() {
		return exerciseId;
	}

	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
