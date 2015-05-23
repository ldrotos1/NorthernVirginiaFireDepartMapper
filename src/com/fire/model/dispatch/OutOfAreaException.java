package com.fire.model.dispatch;

/**
 * This exception indicates that a incident location is outside
 * of the valid response area
 * @author Louis Drotos
 */
public class OutOfAreaException extends Exception {

	private static final long serialVersionUID = 1L;

	public OutOfAreaException() {
		super("The location is outside of the dispatch area.");
	}
}
