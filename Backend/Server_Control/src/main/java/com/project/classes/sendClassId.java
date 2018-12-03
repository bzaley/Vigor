package com.project.classes;
/**
 * 
 * @author Ben Zaley
 *
 */
public class sendClassId {
	private int classId;
	
	public sendClassId() {
		
	}
	/**
	 * Used to return classId alone as a JSON to android.
	 * @param classId
	 */
	public sendClassId(int classId) {
		this.classId = classId;
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
