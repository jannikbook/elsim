package main.java.elsim.simulation;

public class EventAlreadyExistsException extends Exception {
	public EventAlreadyExistsException() {
		super("The specified event already exists in the event queue.");
	}
}
