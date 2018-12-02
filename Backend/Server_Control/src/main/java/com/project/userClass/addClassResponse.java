package com.project.userClass;

public class addClassResponse {

	
	private boolean error;
	private String errorMessage;
	
	public addClassResponse() {
		
	}
	
	public addClassResponse(boolean error, String errorMessage) {
		super();
		this.error = error;
		this.errorMessage = errorMessage;
	}
	public boolean iserror() {
		return error;
	}
	public void seterror(boolean error) {
		this.error = error;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
	
}
