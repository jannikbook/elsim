package main.java.elsim.simulation;

/**
 * An exception that is thrown when the simulation is already running but it is attempted to start it again.
 * @author jbook
 */
public class SimulationAlreadyRunningException extends Exception {
	/**
	 * Creates a {@code SimulationAlreadyRunningException} with a default message.
	 */
	public SimulationAlreadyRunningException() {
		super("The simulation is already running.");
	}
}
