package com.project.user;

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
	
	public LoginResponse(boolean error, String errorMsg, String userEmail, int userId, String firstname, String lastname, String role) {
		this.error = error;
		this.errorMsg = errorMsg;
		this.userEmail = userEmail;
		this.userId = userId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.role = role;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
}
