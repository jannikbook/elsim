package main.java.elsim.simulation;

import main.java.elsim.models.ElevatorShaft;

import java.time.LocalDateTime;

/**
 * Contains all information needed to run a simulation and handles the basic simulation logic.
 */
public class Simulation {
	private LocalDateTime simulationStart;
	private LocalDateTime simulationEnd;

	private ElevatorShaft elevatorShaft;
	private SimEventManager eventManager;

	public Simulation(LocalDateTime simStart, LocalDateTime simEnd, ElevatorShaft elevatorShaft) {
		if (simStart == null) {
			throw new IllegalArgumentException("simStart");
		}

		this.simulationStart = simStart;

		if (simEnd == null) {
			throw new IllegalArgumentException("simEnd");
		}

		this.simulationEnd = simEnd;

		if (elevatorShaft == null) {
			throw new IllegalArgumentException("elevatorShaft");
		}

		this.elevatorShaft = elevatorShaft;
	}

	public void RunSimulation() {
		var event = eventManager.getNextEvent();
		while (event != null) {
			event.processEvent();

			event = eventManager.getNextEvent();
		}

		System.out.println("Simulation has ended.");
	}
}
