package com.project.classes;

public class MakeNewClass {

	private String className;
	private int instructorId;
	private String classDescription;
	private String schedule;
	private String billboard;
	
	public MakeNewClass() {
		
	}
	public MakeNewClass(String className, int instructorId, String classDescription, String schedule, String billboard) {
		this.className = className;
		this.instructorId = instructorId;
		this.classDescription = classDescription;
		this.schedule = schedule;
		this.billboard = billboard;
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
	public String getBillboard() {
		return billboard;
	}
	public void setBillboard(String billboard) {
		this.billboard = billboard;
	}
	@Override
	public String toString() {
		return "MakeNewClass [className=" + className + ", instructorId=" + instructorId + ", classDescription="
				+ classDescription + ", schedule=" + schedule + ", billboard=" + billboard + "]";
	}
	
	
}
