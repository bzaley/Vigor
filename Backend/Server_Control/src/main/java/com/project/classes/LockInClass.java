package com.project.classes;

public class LockInClass {
	
	
	private String newStatus;
	private String newBillboard;
	
	public LockInClass() {
		
	}
	
	public LockInClass(String newStatus, String newBillboard) {

		this.newStatus = newStatus;
		this.newBillboard = newBillboard;
	}



	public String getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}

	public String getNewBillboard() {
		return newBillboard;
	}

	public void setNewBillboard(String newBillboard) {
		this.newBillboard = newBillboard;
	}
	
	
}
