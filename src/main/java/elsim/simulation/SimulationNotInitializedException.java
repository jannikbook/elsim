package main.java.elsim.simulation;

public class SimulationNotInitializedException extends Exception {
	public SimulationNotInitializedException() {
		super("Simulation has not yet been initialized.");
	}
}
