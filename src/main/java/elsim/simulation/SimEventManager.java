package main.java.elsim.simulation;

import main.java.elsim.simulation.events.AbstractSimEvent;

import java.time.LocalDateTime;
import java.util.NavigableSet;
import java.util.TreeSet;

// TODO: Handling of `end` time (don't return events after Simulation.end)
public class SimEventManager {
	private NavigableSet<AbstractSimEvent> events;
	private LocalDateTime lastEventTimestamp;

	/**
	 * Creates a new EventManager instance.
	 */
	public SimEventManager() {
		events = new TreeSet<>(new SimEventTimestampComparator());
	}

	/**
	 * Adds an event to the internal event queue.
	 * @param newEvent The new {@code SimEvent} to add.
	 * @return True iff the event was successfully added to the queue.
	 */
	public void addEvent(AbstractSimEvent newEvent) throws EventWithoutTimestampException, EventAlreadyExistsException {
		if (newEvent.getTimestamp() == null) {
			throw new EventWithoutTimestampException();
		}

		if (!events.add(newEvent)) {
			throw new EventAlreadyExistsException();
		}
	}

	/**
	 * Gets the next event in the simulation and removes it from the internal event queue.
	 * @return The first event in the internal collection of events, based on its timestamp.
	 */
	public AbstractSimEvent getNextEvent() {
		var nextEvent = events.pollFirst();
		if (nextEvent != null) {
			lastEventTimestamp = nextEvent.getTimestamp();
		}
		return nextEvent;
	}

	public LocalDateTime getCurrentTimestamp() {
		return lastEventTimestamp;
	}
}
