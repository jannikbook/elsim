package main.java.elsim.simulation.events;

import main.java.elsim.models.Car;
import main.java.elsim.simulation.EventAlreadyExistsException;
import main.java.elsim.simulation.SimulationNotInitializedException;

/**
 * Describes passengers entering the elevator car at a specific floor.
 */
public class PassengersEnterCarSimEvent extends AbstractSimEvent {
	private final Car car;

	public PassengersEnterCarSimEvent(Car car) throws SimulationNotInitializedException {
		this.car = car;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processEvent() throws SimulationNotInitializedException, EventAlreadyExistsException {
		var elevatorShaft = this.car.getElevatorShaft();
		var currentFloor = elevatorShaft.getCurrentCarFloor();

		var enterDuration = this.car.addAllPassengersAtFloor(currentFloor);
		this.simulation.addSimEvent(enterDuration, new DoorCloseSimEvent(this.car));
	}
}
