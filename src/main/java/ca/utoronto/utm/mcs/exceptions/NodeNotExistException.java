package ca.utoronto.utm.mcs.exceptions;

public class NodeNotExistException extends Exception {

	private static final long serialVersionUID = 6881889110782934305L;

	/**
	 * Sets error exception message.
	 */
	public NodeNotExistException(String message) {
		super(message);
	}
}
