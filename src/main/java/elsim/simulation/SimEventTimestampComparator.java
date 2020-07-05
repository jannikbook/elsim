package main.java.elsim.simulation;

import main.java.elsim.simulation.events.AbstractSimEvent;

import java.util.Comparator;

/**
 * A comparator class that allows comparison of two {@code SimEvent} instances by their timestamp.
 * @author jbook
 */
public class SimEventTimestampComparator implements Comparator<AbstractSimEvent> {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compare(AbstractSimEvent o1, AbstractSimEvent o2) {
		var cprTimestamp = o1.getTimestamp().compareTo(o2.getTimestamp());
		if (cprTimestamp != 0) {
			return cprTimestamp;
		}

		if (o1.getId() > o2.getId()) {
			return 1;
		} else if (o1.getId() < o2.getId()) {
			return -1;
		} else {
			return 0;
		}
	}
}