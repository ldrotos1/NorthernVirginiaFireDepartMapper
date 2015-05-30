package com.fire.exceptions;
 
/**
 * This class represents an error. Its intended to be used
 * to be used in conjunction with the GSON library. Once an
 * error occurs an instance of this class is created, then
 * the GSON library is used to convert this into the following
 * JSON, {error:message}
 * 
 * @author Louis Drotos
 */
public class Error {

	private String error;

	/**
	 * Constructor
	 * @param message The error message
	 */
	public Error(String message) {
		this.error = message;
	}
	
	/**
	 * Getter - Returns the error message.
	 * @return The error message
	 */
	public String getError() {
		return this.error;
	}
}
