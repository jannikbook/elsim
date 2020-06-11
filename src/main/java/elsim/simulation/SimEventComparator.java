package main.java.elsim.simulation;

import java.util.Comparator;

public class SimEventComparator implements Comparator<SimEvent> {
	@Override
	public int compare(SimEvent o1, SimEvent o2) {
		return o1.getTimestamp().compareTo(o2.getTimestamp());
	}
}