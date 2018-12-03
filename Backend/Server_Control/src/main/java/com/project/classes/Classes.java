package com.project.classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/**
 * 
 * @author Ben
 *
 */
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
	/**
	 * 
	 * @param className
	 * @param instructorId
	 * @param classDescription
	 * @param schedule
	 * @param billboard
	 */
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
	/**
	 * 
	 * @return
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * 
	 * @param className
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * 
	 * @return
	 */
	public int getInstructorId() {
		return instructorId;
	}
	/**
	 * 
	 * @param instructorId
	 */
	public void setInstructorId(int instructorId) {
		this.instructorId = instructorId;
	}
	/**
	 * 
	 * @return
	 */
	public String getClassDescription() {
		return classDescription;
	}
	/**
	 * 
	 * @param classDescription
	 */
	public void setClassDescription(String classDescription) {
		this.classDescription = classDescription;
	}
	/**
	 * 
	 * @return
	 */
	public String getSchedule() {
		return schedule;
	}
	/**
	 * 
	 * @param schedule
	 */
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	/**
	 * 
	 * @return
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 
	 * @return
	 */
	public String getBillboard() {
		return billboard;
	}
	/**
	 * 
	 * @param billboard
	 */
	public void setBillboard(String billboard) {
		this.billboard = billboard;
	}
	/**
	 * 
	 * @return
	 */
	public boolean isLocked() {
		return locked;
	}
	/**
	 * 
	 * @param locked
	 */
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	/**
	 * 
	 */
	@Override
	public String toString() {
		return "Classes [classId=" + classId + ", className=" + className + ", instructorId=" + instructorId
				+ ", classDescription=" + classDescription + ", schedule=" + schedule + ", status=" + status
				+ ", billboard=" + billboard + ", locked=" + locked + "]";
	}
	
	
	
}
