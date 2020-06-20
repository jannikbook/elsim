package main.java.elsim.simulation.events;

import java.time.LocalDateTime;

/**
 * An abstract class defining some event occurring at a specific point in time during the simulation.
 */
public abstract class AbstractSimEvent {
	private LocalDateTime timestamp;

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
	public abstract void processEvent();

	/**
	 * Gets the event's timestamp as a {@code LocalDateTime}.
	 * @return The event's timestamp.
	 */
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
}
