package main.java.elsim.simulation.events;

import main.java.elsim.models.Car;
import main.java.elsim.simulation.SimulationNotInitializedException;

import java.util.logging.Logger;

/**
 * Describes passengers entering the elevator car at a specific floor.
 */
public class PassengersEnterCarSimEvent extends AbstractSimEvent {
	private static final Logger LOGGER = Logger.getLogger(PassengersEnterCarSimEvent.class.getName());

	private final Car car;

	public PassengersEnterCarSimEvent(Car car) throws SimulationNotInitializedException {
		this.car = car;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processEvent() throws SimulationNotInitializedException {
		var elevatorShaft = this.car.getElevatorShaft();
		var currentFloor = elevatorShaft.getCurrentCarFloor();
		LOGGER.fine(String.format("Passengers start entering the car at %s", this.getNowFormatted()));

		var enterDuration = this.car.addAllPassengersAtFloor(currentFloor);
		LOGGER.finer(car.getCurrentPassengers().size() + " passengers are now in the car.");
		this.simulation.addSimEvent(enterDuration, new DoorCloseSimEvent(this.car));
	}
}
