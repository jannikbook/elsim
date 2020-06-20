package main.java.elsim.simulation;

import main.java.elsim.simulation.events.AbstractSimEvent;

import java.util.NavigableSet;
import java.util.TreeSet;

public class SimEventManager {
	private NavigableSet<AbstractSimEvent> events;

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
	public boolean addEvent(AbstractSimEvent newEvent) {
		return events.add(newEvent);
	}

	/**
	 * Gets the next event in the simulation and removes it from the internal event queue.
	 * @return The first event in the internal collection of events, based on its timestamp.
	 */
	public AbstractSimEvent getNextEvent() {
		return events.pollFirst();
	}
}
