package com.project.classes;
/**
 * 
 * @author Ben Zaley
 *
 */
public class MakeNewClass {

	private String className;
	private int instructorId;
	private String classDescription;
	private String schedule;
	private String billboard;
	
	public MakeNewClass() {
		
	}
	/**
	 * New class JSON format.
	 * @param className
	 * @param instructorId
	 * @param classDescription
	 * @param schedule
	 * @param billboard
	 */
	public MakeNewClass(String className, int instructorId, String classDescription, String schedule, String billboard) {
		this.className = className;
		this.instructorId = instructorId;
		this.classDescription = classDescription;
		this.schedule = schedule;
		this.billboard = billboard;
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
	
	@Override
	public String toString() {
		return "MakeNewClass [className=" + className + ", instructorId=" + instructorId + ", classDescription="
				+ classDescription + ", schedule=" + schedule + ", billboard=" + billboard + "]";
	}
	
	
}
