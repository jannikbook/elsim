package main.java.elsim.simulation;

import main.java.elsim.simulation.events.AbstractSimEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.logging.Logger;

/**
 * A class managing simulation events.
 * @author jbook
 */
public class SimEventManager {
	private static final Logger LOGGER = Logger.getLogger(SimEventManager.class.getName());

	private final ArrayList<AbstractSimEvent> failedEvents = new ArrayList<>();
	private final NavigableSet<AbstractSimEvent> events;
	private LocalDateTime lastEventTimestamp;

	/**
	 * Creates a new EventManager instance.
	 */
	public SimEventManager(LocalDateTime simulationStart){
		events = new TreeSet<>(new SimEventTimestampComparator());
		this.lastEventTimestamp = simulationStart;
	}

	/**
	 * Adds an event to the internal event queue.
	 * @param newEvent The new {@code SimEvent} to add.
	 * @return True iff the event was successfully added to the queue.
	 */
	public void addEvent(AbstractSimEvent newEvent) throws EventWithoutTimestampException {
		if (newEvent.getTimestamp() == null) {
			throw new EventWithoutTimestampException();
		}

		if (events.add(newEvent)) {
			LOGGER.finest("An event has been added to the event queue: " + newEvent.getClass().getName());
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

	/**
	 * Gets the timestamp of the last event that was retrieved from this instance.
	 * @return A {@code LocalDateTime} instance of the last retrieved event.
	 */
	public LocalDateTime getCurrentTimestamp() {
		return lastEventTimestamp;
	}
}
