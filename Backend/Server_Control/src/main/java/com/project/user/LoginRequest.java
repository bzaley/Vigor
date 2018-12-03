package com.project.user;
/**
 * 
 * @author Ben Zaley
 *
 */
public class LoginRequest {
	private String email;
	private String password;
	
	public LoginRequest() {
		
	}
	/**
	 * JSON login form constructor.
	 * @param email
	 * @param Password
	 */
	public LoginRequest(String email, String Password) {
		super();
		this.email = email;
		this.password = Password;
	}
	/**
	 * 
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
