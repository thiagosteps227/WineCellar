package com.ait.validation;
/**
 * Exception used to indicate some error occurred when processing a 
 * request in a Wine Store.
 */
public abstract class WineException extends Exception {

	/**
	 * Create a new exception with an error message.
	 * @param message a String explaining the error which occurred.
	 */
	protected WineException(final String message) {
		super(message);
	}

	// needed because Exceptions must be serializable
	private static final long serialVersionUID = 6013983962125460401L;

}
