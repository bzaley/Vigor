package com.project.userClass;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
	
	public userClass(int userId, int classId) {
		super();
		this.userId = userId;
		this.classId = classId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	
	
}
