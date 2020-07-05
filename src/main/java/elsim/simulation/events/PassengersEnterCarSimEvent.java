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
		var before = car.getCurrentPassengers().size();

		var enterDuration = this.car.addAllPassengersAtFloor(currentFloor);

		var after = car.getCurrentPassengers().size();
		LOGGER.fine(String.format("%d passengers have entered the car at %s", after - before, this.getNowFormatted()));

		LOGGER.finer(after + " passengers are now in the car.");
		this.simulation.addSimEvent(enterDuration, new DoorCloseSimEvent(this.car));
	}
}
