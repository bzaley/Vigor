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
	private int classId;
	private int userId;
	
	public userClass() {
		
	}
	
	public userClass(int classId, int userId) {
		super();
		this.classId = classId;
		this.userId = userId;
	}
	
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
	
	
}
