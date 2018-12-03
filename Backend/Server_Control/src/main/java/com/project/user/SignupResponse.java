package com.project.user;
/**
 * 
 * @author Ben Zaley
 *
 */
public class SignupResponse {
	private boolean error;
	private String errorMsg;
	
	public SignupResponse() {
		
	}
	/**
	 * Sign up JSON error response constructor
	 * @param error
	 * @param errorMsg
	 */
	public SignupResponse(boolean error, String errorMsg) {
		this.error = error;
		this.errorMsg = errorMsg;
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
	
	
}
