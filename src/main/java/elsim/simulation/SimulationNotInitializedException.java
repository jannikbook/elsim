package main.java.elsim.simulation;

/**
 * An exception that is thrown when a simulation is run without being initialized first.
 */
public class SimulationNotInitializedException extends Exception {
	/**
	 * Creates a {@code SimulationNotInitializedException} with a default message.
	 */
	public SimulationNotInitializedException() {
		super("Simulation has not yet been initialized.");
	}
}
