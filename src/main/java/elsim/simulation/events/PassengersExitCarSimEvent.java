package main.java.elsim.simulation.events;

import main.java.elsim.models.Car;
import main.java.elsim.simulation.EventAlreadyExistsException;
import main.java.elsim.simulation.SimulationNotInitializedException;

import java.util.logging.Logger;

/**
 * Describes passengers exiting the elevator at a specific floor.
 */
public class PassengersExitCarSimEvent extends AbstractSimEvent {
	private static final Logger LOGGER = Logger.getLogger(PassengersExitCarSimEvent.class.getName());


	private final Car car;

	public PassengersExitCarSimEvent(Car car) throws SimulationNotInitializedException {
		this.car = car;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processEvent() throws SimulationNotInitializedException, EventAlreadyExistsException {
		var elevatorShaft = this.car.getElevatorShaft();
		var currentFloor = elevatorShaft.getCurrentCarFloor();

		var exitDuration = this.car.removeAllPassengersAtFloor(currentFloor);
		LOGGER.fine("After passengers exiting, " + car.getCurrentPassengers().size() + " passengers remain.");
		this.simulation.addSimEvent(exitDuration, new PassengersEnterCarSimEvent(this.car));
	}
}
