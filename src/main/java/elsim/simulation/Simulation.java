package main.java.elsim.simulation;

import main.java.elsim.models.ElevatorShaft;
import main.java.elsim.simulation.events.AbstractSimEvent;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Contains all information needed to run a simulation and handles the basic simulation logic.
 */
public class Simulation {
	private static Simulation instance;

	private LocalDateTime simulationStart;
	private LocalDateTime simulationEnd;

	private ElevatorShaft elevatorShaft;
	private SimEventManager eventManager;

	private boolean simulationIsRunning = false;

	public static Simulation getInstance() throws SimulationNotInitializedException {
		if (instance == null) {
			throw new SimulationNotInitializedException();
		}

		return instance;
	}

	public static void initialize(LocalDateTime start, LocalDateTime end) throws SimulationAlreadyInitializedException {
		if (instance != null) {
			throw new SimulationAlreadyInitializedException();
		}

		instance = new Simulation(start, end);
	}

	private Simulation(LocalDateTime simStart, LocalDateTime simEnd) {
		if (simStart == null) {
			throw new IllegalArgumentException("simStart");
		}

		this.simulationStart = simStart;

		if (simEnd == null) {
			throw new IllegalArgumentException("simEnd");
		}

		this.simulationEnd = simEnd;
	}

	public void addSimEvent(int secondsFromNow, AbstractSimEvent simEvent) throws EventAlreadyExistsException {
		addSimEvent(Duration.ofSeconds(secondsFromNow), simEvent);
	}

	public void addSimEvent(Duration timeFromNow, AbstractSimEvent simEvent) throws EventAlreadyExistsException {
		var current = eventManager.getCurrentTimestamp();
		simEvent.setTimestamp(current.plus(timeFromNow));
		try {
			eventManager.addEvent(simEvent);
		}
		catch (EventWithoutTimestampException e) {
			// This should never happen.
		}
	}

	public void runSimulation() throws SimulationAlreadyRunningException {
		if (simulationIsRunning) {
			throw new SimulationAlreadyRunningException();
		}

		simulationIsRunning = true;

		// TODO: add first event

		var event = eventManager.getNextEvent();
		while (event != null) {
			try {
				event.processEvent();
			}
			catch (SimulationNotInitializedException exc) {
				System.err.println("Caught SimulationNotInitializedException while processing event:");
				exc.printStackTrace(System.err);
			}

			event = eventManager.getNextEvent();
		}

		System.out.println("Simulation has ended.");
	}
}
