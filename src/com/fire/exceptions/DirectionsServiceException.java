package com.fire.exceptions;

/**
 * This exception indicates that an error occurred when calling the
 * MapQuest Directions API Service 
 * @author Louis Drotos
 */
public class DirectionsServiceException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public DirectionsServiceException() {
		
		super("The call to the MapQuest Directions API failed.");
	}
}
