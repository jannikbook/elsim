package main.java.elsim.simulation;

/**
 * An exception that is thrown when an event is used that does not have an associated timestamp.
 * This is unlikely and should not happen under normal execution conditions.
 * @author jbook
 */
public class EventWithoutTimestampException extends Exception {
	/**
	 * Creates an {@code EventWithoutTimestampException} instance with a default message.
	 */
	public EventWithoutTimestampException() {
		super("The specified event does not contain a timestamp. A timestamp is necessary.");
	}
}
