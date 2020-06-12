package main.java.elsim.simulation;

import java.util.SortedSet;
import java.util.TreeSet;

public class SimEventManager {
	private SortedSet<AbstractSimEvent> events;

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
}
