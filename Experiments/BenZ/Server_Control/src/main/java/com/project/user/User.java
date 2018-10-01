package com.project.user;

public class User {

	
	private int user_id;
	private String user_email;
	private String firstname;
	private String lastname;
	private String password;
	private String role;
	
	public User() {
		
	}
	
	public User(int user_id, String user_email, String firstname, String lastname, String password, String role) {
		super();
		this.user_id = user_id;
		this.user_email = user_email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.role = role;
	}
	
	
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
}
