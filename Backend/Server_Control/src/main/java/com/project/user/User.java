package com.project.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/**
 * 
 * @author Ben Zaley
 *
 */
@Entity //JPA knows to create table of this type
public class User {

	@Id //Tells JPA what the primary key is
	@GeneratedValue //auto increments the value when a new row is created
	@Column(columnDefinition ="serial")
	private int userId;
	private String userEmail;
	private String firstname;
	private String lastname;
	private String password;
	private String role;
	
	public User() {
		
	}
	/**
	 * User table/object constructor 
	 * @param userId
	 * @param userEmail
	 * @param firstname
	 * @param lastname
	 * @param password
	 * @param role
	 */
	public User(int userId, String userEmail, String firstname, String lastname, String password, String role) {
		super();
		this.userId = userId;
		this.userEmail = userEmail;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.role = role;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getuserId() {
		return userId;
	}
	/**
	 * 
	 * @param userId
	 */
	public void setuserId(int userId) {
		this.userId = userId;
	}
	/**
	 * 
	 * @return
	 */
	public String getuserEmail() {
		return userEmail;
	}
	/**
	 * 
	 * @param userEmail
	 */
	public void setuserEmail(String userEmail) {
		this.userEmail = userEmail;
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
