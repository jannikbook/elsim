package main.java.elsim.simulation.events;

import main.java.elsim.models.Car;
import main.java.elsim.simulation.EventAlreadyExistsException;
import main.java.elsim.simulation.SimulationNotInitializedException;

/**
 * Describes the elevator car doors opening.
 */
public class DoorOpenSimEvent extends AbstractSimEvent {
	private final Car car;

	public DoorOpenSimEvent(Car car) throws SimulationNotInitializedException {
		this.car = car;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processEvent() throws SimulationNotInitializedException, EventAlreadyExistsException {
		var durationInSeconds = (int)this.car.openDoor();
		this.simulation.addSimEvent(durationInSeconds, new PassengersExitCarSimEvent(this.car));
	}
}
