package main.java.elsim.simulation.events;

import main.java.elsim.simulation.Simulation;
import main.java.elsim.simulation.SimulationNotInitializedException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * An abstract class defining some event occurring at a specific point in time during the simulation.
 * @author jbook
 */
public abstract class AbstractSimEvent {
	private static long counter = 0;

	private final long id;
	protected Simulation simulation;
	protected LocalDateTime timestamp;

	/**
	 * Create an event that should be processed at a specific time during the simulation.
	 * @throws SimulationNotInitializedException When the simulation has not been initialized yet.
	 */
	public AbstractSimEvent() throws SimulationNotInitializedException {
		this.id = counter++;
		this.simulation = Simulation.getInstance();
	}

	/**
	 * Process this event instance.
	 */
	public abstract void processEvent() throws SimulationNotInitializedException;

	/**
	 * Gets the event's timestamp as a {@code LocalDateTime}.
	 * @return The event's timestamp.
	 */
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public long getId() {
		return this.id;
	}

	/**
	 * Sets the event's timestamp. Only possible to set once.
	 * @param timestamp The event's new timestamp.
	 */
	public void setTimestamp(LocalDateTime timestamp) {
		if (this.timestamp != null) {
			throw new UnsupportedOperationException("timestamp can only be set once.");
		}

		this.timestamp = timestamp;
	}

	protected String getNowFormatted() {
		return formatTimestamp(this.timestamp);
	}

	protected String formatTimestamp(LocalDateTime timestamp) {
		return timestamp.format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy"));
	}
}
