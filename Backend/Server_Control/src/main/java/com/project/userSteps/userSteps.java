package com.project.userSteps;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/**
 * 
 * @author Ben Zaley
 *
 */
@Entity
public class userSteps {
	
	@Id
	@GeneratedValue //auto increments the value when a new row is created
	@Column(columnDefinition ="serial")
	private int entry; //Simple counting variable to allow for multiple instances of a users step entries. Not part of userSteps object.
	private int userId;
	private String date;
	private int steps;
	private int stepGoal;
	private boolean goalMet;
	
	public userSteps() {
	}
	/**
	 * Constructor for userSteps table and object.
	 * @param userId
	 * @param date
	 * @param steps
	 * @param stepGoal
	 */
	public userSteps(int userId, String date, int steps, int stepGoal) {
		super();
		
		this.userId = userId;
		this.date = date;
		this.steps = steps;
		this.stepGoal = stepGoal;
		this.goalMet = false;
	}


	
	/**
	 * 
	 * @return
	 */
	public boolean isGoalMet() {
		return goalMet;
	}
	/**
	 * 
	 * @param goalMet
	 */
	public void setGoalMet(boolean goalMet) {
		this.goalMet = goalMet;
	}
	/**
	 * 
	 * @return
	 */
	public int getStepGoal() {
		return stepGoal;
	}
	/**
	 * 
	 * @param stepGoal
	 */
	public void setStepGoal(int stepGoal) {
		this.stepGoal = stepGoal;
	}
	/**
	 * 
	 * @return
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * 
	 * @param userId
	 */
	public void setUser_id(int userId) {
		this.userId = userId;
	}
	/**
	 * 
	 * @return
	 */
	public String getDate() {
		return date;
	}
	/**
	 * 
	 * @param date
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * 
	 * @return
	 */
	public int getSteps() {
		return steps;
	}
	/**
	 * 
	 * @param steps
	 */
	public void setSteps(int steps) {
		this.steps = steps;
	}
	
	
}
