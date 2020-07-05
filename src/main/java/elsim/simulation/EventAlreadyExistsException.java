package main.java.elsim.simulation;

/**
 * An exception that is thrown where an event is only expected once, but is used or supplied more than once.
 * @author jbook
 */
public class EventAlreadyExistsException extends Exception {
	/**
	 * Creates an {@code EventAlreadyExistsException} with a default message.
	 */
	public EventAlreadyExistsException() {
		super("The specified event already exists in the event queue.");
	}
}
