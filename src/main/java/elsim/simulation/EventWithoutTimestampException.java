package main.java.elsim.simulation;

public class EventWithoutTimestampException extends Exception {
	public EventWithoutTimestampException() {
		super("The specified event does not contain a timestamp. A timestamp is necessary.");
	}
}
