package com.project.classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;



@Entity
public class Classes {
	@Id
	@GeneratedValue
	@Column(columnDefinition = "serial")
	private int classId;
	private String className;
	private int instructorId;
	private String classDescription;
	private String schedule;
	private String status;
	private String billboard;
	private boolean locked;
	
	public Classes() {
		
	}
	
	public Classes(String className, int instructorId, String classDescription, String schedule, String billboard) {
		super();
		
		this.className = className;
		this.instructorId = instructorId;
		this.classDescription = classDescription;
		this.schedule = schedule;
		this.status = " ";
		this.billboard = billboard;
		this.locked = false;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(int instructorId) {
		this.instructorId = instructorId;
	}

	public String getClassDescription() {
		return classDescription;
	}

	public void setClassDescription(String classDescription) {
		this.classDescription = classDescription;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBillboard() {
		return billboard;
	}

	public void setBillboard(String billboard) {
		this.billboard = billboard;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	@Override
	public String toString() {
		return "Classes [classId=" + classId + ", className=" + className + ", instructorId=" + instructorId
				+ ", classDescription=" + classDescription + ", schedule=" + schedule + ", status=" + status
				+ ", billboard=" + billboard + ", locked=" + locked + "]";
	}
	
	
	
}
