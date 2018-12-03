package com.project.user;

/**
 * 
 * @author Ben Zaley
 *
 */
public class LoginResponse {
	private boolean error;
	private String errorMsg;
	private String userEmail;
	private int userId;
	private String firstname;
	private String lastname;
	private String role;
	
	public LoginResponse() {
		
	}
	/**
	 * JSON response constructor.
	 * @param error
	 * @param errorMsg
	 * @param userEmail
	 * @param userId
	 * @param firstname
	 * @param lastname
	 * @param role
	 */
	public LoginResponse(boolean error, String errorMsg, String userEmail, int userId, String firstname, String lastname, String role) {
		this.error = error;
		this.errorMsg = errorMsg;
		this.userEmail = userEmail;
		this.userId = userId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.role = role;
	}
	/**
	 * 
	 * @return
	 */
	public boolean isError() {
		return error;
	}
	/**
	 * 
	 * @param error
	 */
	public void setError(boolean error) {
		this.error = error;
	}
	/**
	 * 
	 * @return
	 */
	public String getErrorMsg() {
		return errorMsg;
	}
	/**
	 * 
	 * @param errorMsg
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	/**
	 * 
	 * @return
	 */
	public String getUserEmail() {
		return userEmail;
	}
	/**
	 * 
	 * @param userEmail
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
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
	public String getFirstname() {
		return firstname;
	}
	/**
	 * 
	 * @param firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	/**
	 * 
	 * @return
	 */
	public String getLastname() {
		return lastname;
	}
	/**
	 * 
	 * @param lastname
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	/**
	 * 
	 * @return
	 */
	public String getRole() {
		return role;
	}
	/**
	 * 
	 * @param role
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
	
}
