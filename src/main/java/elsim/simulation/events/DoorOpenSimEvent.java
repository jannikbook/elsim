package main.java.elsim.simulation.events;

import main.java.elsim.models.Car;
import main.java.elsim.simulation.SimulationNotInitializedException;

import java.util.logging.Logger;

/**
 * Describes the elevator car doors opening.
 */
public class DoorOpenSimEvent extends AbstractSimEvent {
	private static final Logger LOGGER = Logger.getLogger(DoorOpenSimEvent.class.getName());

	private final Car car;

	public DoorOpenSimEvent(Car car) throws SimulationNotInitializedException {
		this.car = car;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processEvent() throws SimulationNotInitializedException {
		var durationInSeconds = (int)this.car.openDoor();
		LOGGER.info(String.format("Door is opening and will be open at %s.",
				this.formatTimestamp(this.timestamp.plusSeconds(durationInSeconds))));

		this.simulation.addSimEvent(durationInSeconds, new PassengersExitCarSimEvent(this.car));
	}
}
