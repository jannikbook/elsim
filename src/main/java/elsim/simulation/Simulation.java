package main.java.elsim.simulation;

import main.java.elsim.models.ElevatorShaft;
import main.java.elsim.simulation.events.AbstractSimEvent;
import main.java.elsim.simulation.events.DoorOpenSimEvent;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Contains all information needed to run a simulation and handles the basic simulation logic.
 */
public class Simulation {
	private static Simulation instance;

	private final LocalDateTime simulationStart;
	private final LocalDateTime simulationEnd;

	private final ElevatorShaft elevatorShaft;
	private final SimEventManager eventManager;

	private boolean simulationIsRunning = false;

	public static Simulation getInstance() throws SimulationNotInitializedException {
		if (instance == null) {
			throw new SimulationNotInitializedException();
		}

		return instance;
	}

	public static void initialize(ElevatorShaft shaft, SimEventManager eventManager, LocalDateTime start, LocalDateTime end) throws SimulationAlreadyInitializedException {
		if (instance != null) {
			throw new SimulationAlreadyInitializedException();
		}

		instance = new Simulation(shaft, eventManager, start, end);
	}

	private Simulation(ElevatorShaft shaft, SimEventManager eventManager, LocalDateTime simStart, LocalDateTime simEnd) {
		if (shaft == null) {
			throw new IllegalArgumentException("shaft");
		}

		this.elevatorShaft = shaft;

		if (eventManager == null) {
			throw new IllegalArgumentException("eventManager");
		}

		this.eventManager = eventManager;

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

	public static void run() throws SimulationAlreadyRunningException, SimulationNotInitializedException {
		var sim = getInstance();

		if (sim.simulationIsRunning) {
			throw new SimulationAlreadyRunningException();
		}

		sim.simulationIsRunning = true;


		var eventManager = sim.eventManager;

		var car = sim.elevatorShaft.getElevatorCar();
		var startEvent = new DoorOpenSimEvent(car);
		startEvent.setTimestamp(sim.simulationStart);

		try {
			eventManager.addEvent(startEvent);
		} catch (EventWithoutTimestampException | EventAlreadyExistsException e) {
			e.printStackTrace(); // log
		}

		var event = eventManager.getNextEvent();
		while (event != null && event.getTimestamp().isBefore(sim.simulationEnd)) {
			try {
				event.processEvent();
			}
			catch (SimulationNotInitializedException notInitializedException) {
				System.err.println("Caught SimulationNotInitializedException while processing event:");
				notInitializedException.printStackTrace(System.err);
			}
			catch (EventAlreadyExistsException alreadyExistsException) {
				System.err.println("Caught EventAlreadyExistsException while processing events:");
				alreadyExistsException.printStackTrace(System.err);
			}

			event = eventManager.getNextEvent();
		}

		System.out.println("Simulation has ended.");
	}
}
