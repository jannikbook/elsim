package main.java.elsim.simulation.events;

import main.java.elsim.simulation.EventAlreadyExistsException;
import main.java.elsim.simulation.Simulation;
import main.java.elsim.simulation.SimulationNotInitializedException;

import java.time.LocalDateTime;

/**
 * An abstract class defining some event occurring at a specific point in time during the simulation.
 * @author jbook
 */
public abstract class AbstractSimEvent {
	protected Simulation simulation;
	private LocalDateTime timestamp;

	public AbstractSimEvent() throws SimulationNotInitializedException {
		this.simulation = Simulation.getInstance();
	}

	/**
	 * Create an event that should be processed at a given time during the simulation.
	 * @param timestamp When the event occurs.
	 */
	public AbstractSimEvent(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Process this event instance.
	 */
	public abstract void processEvent() throws SimulationNotInitializedException, EventAlreadyExistsException;

	/**
	 * Gets the event's timestamp as a {@code LocalDateTime}.
	 * @return The event's timestamp.
	 */
	public LocalDateTime getTimestamp() {
		return timestamp;
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
}
