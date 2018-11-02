package com.project.userSteps;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class userSteps {
	
	@Id
	@GeneratedValue //auto increments the value when a new row is created
	@Column(columnDefinition ="serial")
	private int entry; //Simple counting variable to allow for multiple instances of a users step entries. Not part of userSteps object.
	private int userId;
	private String date;
	private int steps;
	
	public userSteps() {
	}
	
	public userSteps(int userId, String date, int steps) {
		super();
		
		this.userId = userId;
		this.date = date;
		this.steps = steps;
	}


	public int getUserId() {
		return userId;
	}

	public void setUser_id(int userId) {
		this.userId = userId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}
	
	
}
