package main.java.elsim.simulation;

import main.java.elsim.models.ElevatorShaft;
import main.java.elsim.simulation.events.AbstractSimEvent;
import main.java.elsim.simulation.events.DoorOpenSimEvent;
import main.java.elsim.simulation.events.PassengerLeavesFloorSimEvent;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.logging.Logger;

/**
 * Contains all information needed to run a simulation and handles the basic simulation logic.
 *
 * The methods {@code initialize} and {@code run} are the public interfaces for simulation.
 * All needed information should be supplied as parameters for the {@code initialize} method call.
 * Then, {@code run} can be called to start the simulation.
 * To configure logging output, the static logging interfaces should be used.
 *
 * @author jbook
 */
public class Simulation {
	private static final Logger LOGGER = Logger.getLogger(Simulation.class.getName());

	private static Simulation instance;

	private final LocalDateTime simulationStart;
	private final LocalDateTime simulationEnd;

	private final ElevatorShaft elevatorShaft;
	private final SimEventManager eventManager;

	private boolean simulationIsRunning = false;

	/**
	 * Gets the singleton {@code Simulation} instance if it exists, and throws a {@code SimulationNotInitializedException} when it doesn't.
	 * @return The {@code Simulation} instance.
	 * @throws SimulationNotInitializedException When the {@code initialize} method has not been called beforehand.
	 */
	public static Simulation getInstance() throws SimulationNotInitializedException {
		if (instance == null) {
			throw new SimulationNotInitializedException();
		}

		return instance;
	}

	/**
	 * Initializes the static {@code Simulation} instance with all information necessary for running the simulation.
	 * @param shaft The elevator shaft to simulate.
	 * @param eventManager The event manager to use for simulation events.
	 * @param start The point at which the simulation starts.
	 * @param end The point at which the simulation ends, even when there are still events after this point in time.
	 * @throws SimulationAlreadyInitializedException When this method is called more than once.
	 */
	public static void initialize(ElevatorShaft shaft, SimEventManager eventManager, LocalDateTime start, LocalDateTime end) throws SimulationAlreadyInitializedException {
		if (instance != null) {
			throw new SimulationAlreadyInitializedException();
		}

		instance = new Simulation(shaft, eventManager, start, end);

		for (var f : instance.elevatorShaft.getFloors()) {
			try {
				f.loadPassengers(instance, instance.elevatorShaft);
			}
			catch (SimulationNotInitializedException e) {} // literally impossible
		}

		LOGGER.info("Simulation has been initialized.");
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

	/**
	 * Add an event to the simulation's event queue.
	 * @param secondsFromNow The offset in seconds after which to add the event.
	 * @param simEvent The event to add.
	 */
	public void addSimEvent(int secondsFromNow, AbstractSimEvent simEvent) {
		addSimEvent(Duration.ofSeconds(secondsFromNow), simEvent);
	}

	/**
	 * Add an event to the simulation's event queue.
	 * @param timeFromNow The offset after which to add the event.
	 * @param simEvent The event to add.
	 */
	public void addSimEvent(Duration timeFromNow, AbstractSimEvent simEvent) {
		var current = eventManager.getCurrentTimestamp();
		simEvent.setTimestamp(current.plus(timeFromNow));
		try {
			eventManager.addEvent(simEvent);
		}
		catch (EventWithoutTimestampException e) {
			// This should never happen.
		}
	}

	/**
	 * Start the simulation.
	 * @throws SimulationAlreadyRunningException Thrown when this method has been called before.
	 * @throws SimulationNotInitializedException Thrown when {@code initialize} has not been called before calling this method.
	 */
	public static void run() throws SimulationAlreadyRunningException, SimulationNotInitializedException {
		var sim = getInstance();

		if (sim.simulationIsRunning) {
			throw new SimulationAlreadyRunningException();
		}

		sim.simulationIsRunning = true;

		LOGGER.info("");
		LOGGER.info("*** SIMULATION STARTING ***");
		LOGGER.info("");

		var eventManager = sim.eventManager;

		var car = sim.elevatorShaft.getElevatorCar();
		var startEvent = new DoorOpenSimEvent(car);
		startEvent.setTimestamp(sim.simulationStart);

		try {
			eventManager.addEvent(startEvent);
		} catch (EventWithoutTimestampException withoutTimestampException) {
			LOGGER.severe(withoutTimestampException.toString());
		}

		var event = eventManager.getNextEvent();
		while (event != null && event.getTimestamp().isBefore(sim.simulationEnd)) {
			try {
				LOGGER.finest("Executing event: " + event.getClass().getName());
				event.processEvent();
			}
			catch (SimulationNotInitializedException exception) {
				LOGGER.severe(exception.toString());
			}

			event = eventManager.getNextEvent();
		}

		LOGGER.info("");
		LOGGER.info("*** SIMULATION HAS ENDED ***");
		LOGGER.info("");
	}

	public LocalDateTime getEnd() {
		return this.simulationEnd;
	}

	public LocalDateTime getStart() {
		return this.simulationStart;
	}
}
