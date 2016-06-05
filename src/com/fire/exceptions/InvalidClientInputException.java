package com.fire.exceptions;

/**
 * This exception indicates that the client provided a invalid input
 * @author Louis Drotos
 *
 */
public class InvalidClientInputException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidClientInputException( String message ) {
		
		super( message);
	}
}
