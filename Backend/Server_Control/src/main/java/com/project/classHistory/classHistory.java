package com.project.classHistory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
	
	public classHistory(int userId, int classId, String date, String billBoard, String notes) {
		super();
		this.userId = userId;
		this.classId = classId;
		this.date = date;
		this.billBoard = billBoard;
		this.notes = notes;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getBillBoard() {
		return billBoard;
	}
	public void setBillBoard(String billBoard) {
		this.billBoard = billBoard;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
}
