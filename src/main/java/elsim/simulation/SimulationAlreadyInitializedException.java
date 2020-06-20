package main.java.elsim.simulation;

public class SimulationAlreadyInitializedException extends Exception {
	public SimulationAlreadyInitializedException() {
		super("The simulation has already been initialized.");
	}
}
