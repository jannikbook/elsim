package main.java.elsim.simulation.events;

import main.java.elsim.models.Car;
import main.java.elsim.simulation.SimulationNotInitializedException;

import java.util.logging.Logger;

/**
 * Describes the elevator car doors closing.
 */
public class DoorCloseSimEvent extends AbstractSimEvent {
	private static final Logger LOGGER = Logger.getLogger(DoorCloseSimEvent.class.getName());

	private final Car car;

	public DoorCloseSimEvent(Car car) throws SimulationNotInitializedException {
		this.car = car;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processEvent() throws SimulationNotInitializedException {
		var durationInSeconds = (int)this.car.closeDoor();
		LOGGER.info(String.format("Door is closing and will be closed at %s.",
				this.formatTimestamp(this.timestamp.plusSeconds(durationInSeconds))));

		this.simulation.addSimEvent(durationInSeconds, new CarMoveSimEvent(this.car.getElevatorShaft()));
	}
}
