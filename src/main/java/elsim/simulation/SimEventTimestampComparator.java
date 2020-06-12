package main.java.elsim.simulation;

import java.util.Comparator;

/**
 * A comparator class that allows comparison of two {@code SimEvent} instances by their timestamp.
 */
public class SimEventTimestampComparator implements Comparator<AbstractSimEvent> {
	@Override
	public int compare(AbstractSimEvent o1, AbstractSimEvent o2) {
		return o1.getTimestamp().compareTo(o2.getTimestamp());
	}
}