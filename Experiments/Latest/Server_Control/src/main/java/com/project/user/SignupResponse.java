package com.project.user;

public class SignupResponse {
	private boolean error;
	private String errorMsg;
	
	public SignupResponse() {
		
	}
	
	public SignupResponse(boolean error, String errorMsg) {
		this.error = error;
		this.errorMsg = errorMsg;
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
	
	
}
