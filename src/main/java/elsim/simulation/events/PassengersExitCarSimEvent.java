package main.java.elsim.simulation.events;

import main.java.elsim.models.Car;
import main.java.elsim.simulation.EventAlreadyExistsException;
import main.java.elsim.simulation.SimulationNotInitializedException;

public class PassengersExitCarSimEvent extends AbstractSimEvent {
	private final Car car;

	public PassengersExitCarSimEvent(Car car) throws SimulationNotInitializedException {
		this.car = car;
	}

	@Override
	public void processEvent() throws SimulationNotInitializedException, EventAlreadyExistsException {
		var elevatorShaft = this.car.getElevatorShaft();
		var currentFloor = elevatorShaft.getCurrentCarFloor();

		var exitDuration = this.car.removeAllPassengersAtFloor(currentFloor);
		this.simulation.addSimEvent(exitDuration, new PassengersEnterCarSimEvent(this.car));
	}
}
