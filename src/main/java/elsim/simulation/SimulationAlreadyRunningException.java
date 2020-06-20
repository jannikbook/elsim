package main.java.elsim.simulation;

public class SimulationAlreadyRunningException extends Exception {
	public SimulationAlreadyRunningException() {
		super("The simulation is already running.");
	}
}
