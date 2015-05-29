package com.fire.exceptions;

/**
 * This exception is thrown when the validator fails.
 * 
 * @author Louis Drotos
 */
public class ValidatorException extends Exception {

	private static final long serialVersionUID = 1L;

	public ValidatorException() {
		super("Validation error");
	}
	
	public ValidatorException(String message) {
		super(message);
	}
}
