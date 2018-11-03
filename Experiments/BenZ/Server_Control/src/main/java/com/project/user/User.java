package com.project.user;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity //JPA knows to create table of this type
public class User {

	@Id //Tells JPA what the primary key is
	private int userId;
	private String user_email;
	private String firstname;
	private String lastname;
	private String password;
	private String role;
	
	public User() {
		
	}
	
	public User(int userId, String user_email, String firstname, String lastname, String password, String role) {
		super();
		this.userId = userId;
		this.user_email = user_email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.role = role;
	}
	
	
	
	public int getuserId() {
		return userId;
	}
	public void setuserId(int userId) {
		this.userId = userId;
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
