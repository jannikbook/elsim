package main.java.elsim.simulation;

/**
 * An exception that is thrown when a simulation has been initialized and it is attempted to initialize it again.
 * @author jbook
 */
public class SimulationAlreadyInitializedException extends Exception {
	/**
	 * Creates a {@code SimulationAlreadyInitializedException} with a default message.
	 */
	public SimulationAlreadyInitializedException() {
		super("The simulation has already been initialized.");
	}
}
