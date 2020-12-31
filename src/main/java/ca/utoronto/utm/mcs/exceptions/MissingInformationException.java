package ca.utoronto.utm.mcs.exceptions;

public class MissingInformationException extends Exception {

	private static final long serialVersionUID = -6563355518980003423L;

	/**
	 * Sets error exception message.
	 */
	public MissingInformationException(String message) {
		super(message);
	}
}
