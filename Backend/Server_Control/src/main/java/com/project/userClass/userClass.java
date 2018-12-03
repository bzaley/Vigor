package com.project.userClass;

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
public class userClass {

	
	@Id
	@GeneratedValue 
	@Column(columnDefinition ="serial")
	private int entry;
	private int userId;
	private int classId;
	
	public userClass() {
		
	}
	/**
	 * 
	 * @param userId
	 * @param classId
	 */
	public userClass(int userId, int classId) {
		super();
		this.userId = userId;
		this.classId = classId;
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
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * 
	 * @return
	 */
	public int getClassId() {
		return classId;
	}
	/**
	 * 
	 * @param classId
	 */
	public void setClassId(int classId) {
		this.classId = classId;
	}
	
	
}
