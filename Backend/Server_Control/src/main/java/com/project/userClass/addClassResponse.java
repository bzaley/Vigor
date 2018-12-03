package com.project.userClass;
/**
 * 
 * @author Ryan Ingram
 *
 */
public class addClassResponse {

	
	private boolean error;
	private String errorMessage;
	
	public addClassResponse() {
		
	}
	/**
	 * JSON error response
	 * @param error
	 * @param errorMessage
	 */
	public addClassResponse(boolean error, String errorMessage) {
		super();
		this.error = error;
		this.errorMessage = errorMessage;
	}
	/**
	 * 
	 * @return
	 */
	public boolean iserror() {
		return error;
	}
	/**
	 * 
	 * @param error
	 */
	public void seterror(boolean error) {
		this.error = error;
	}
	/**
	 * 
	 * @return
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	/**
	 * 
	 * @param errorMessage
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
	
}
