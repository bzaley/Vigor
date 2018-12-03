package com.project.Exercise;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/**
 * 
 * @author Ryan Ingram
 *
 */
@Entity
public class Exercise {

	@Id
	@GeneratedValue 
	@Column(columnDefinition ="serial")
	private int exerciseId;
	private String name;
	
	public Exercise() {
		
	}
	/**
	 * Format for exercise identification table
	 * @param exerciseId
	 * @param name
	 */
	public Exercise(int exerciseId, String name) {
		super();
		this.exerciseId = exerciseId;
		this.name = name;
	}
	/**
	 * 
	 * @return
	 */
	public int getExerciseId() {
		return exerciseId;
	}
	/**
	 * 
	 * @param exerciseId
	 */
	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}
	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
