package ca.utoronto.utm.mcs.exceptions;

public class NodeAlreadyExistsException extends Exception {

	
	private static final long serialVersionUID = -8277901540097603449L;

	/**
	 * Sets error exception message.
	 */
	public NodeAlreadyExistsException(String message) {
		super(message);
	}
}
