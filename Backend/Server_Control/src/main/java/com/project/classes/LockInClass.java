package com.project.classes;
/**
 * 
 * @author Ben
 *
 */
public class LockInClass {




	private String newStatus;
	private String newBillboard;

	public LockInClass() {

	}
	/**
	 * Form for lock-in JSON.
	 * @param newStatus
	 * @param newBillboard
	 */
	public LockInClass(String newStatus, String newBillboard) {

		this.newStatus = newStatus;
		this.newBillboard = newBillboard;
	}


	/**
	 * 
	 * @return
	 */
	public String getNewStatus() {
		return newStatus;
	}
	/**
	 * 
	 * @param newStatus
	 */
	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}
	/**
	 * 
	 * @return
	 */
	public String getNewBillboard() {
		return newBillboard;
	}
	/**
	 * 
	 * @param newBillboard
	 */
	public void setNewBillboard(String newBillboard) {
		this.newBillboard = newBillboard;
	}


}
