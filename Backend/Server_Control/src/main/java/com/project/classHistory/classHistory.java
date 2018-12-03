package com.project.classHistory;

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
public class classHistory {

	
	@Id
	@GeneratedValue 
	@Column(columnDefinition ="serial")
	private int entry;
	private int userId;
	private int classId;
	private String date;
	private String billBoard;
	private String notes;
	
	public classHistory() {
		
	}
	/**
	 * Constructor for archived classes in database.
	 * @param userId
	 * @param classId
	 * @param date
	 * @param billBoard
	 * @param notes
	 */
	public classHistory(int userId, int classId, String date, String billBoard, String notes) {
		super();
		this.userId = userId;
		this.classId = classId;
		this.date = date;
		this.billBoard = billBoard;
		this.notes = notes;
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
	public String getBillBoard() {
		return billBoard;
	}
	/**
	 * *
	 * @param billBoard
	 */
	public void setBillBoard(String billBoard) {
		this.billBoard = billBoard;
	}
	/**
	 * 
	 * @return
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * 
	 * @param notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
}
