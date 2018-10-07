package com.project.userSteps;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class userSteps {
	
	@Id
	private int user_id;
	private int date;
	private int steps;
	
	public userSteps() {
	}
	
	public userSteps(int user_id, int date, int steps) {
		super();
		this.user_id = user_id;
		this.date = date;
		this.steps = steps;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}
	
	
}
