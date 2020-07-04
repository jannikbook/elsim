package main.java.elsim.simulation.events;

import main.java.elsim.models.Car;
import main.java.elsim.simulation.EventAlreadyExistsException;
import main.java.elsim.simulation.SimulationNotInitializedException;

public class DoorCloseSimEvent extends AbstractSimEvent {
	private final Car car;

	public DoorCloseSimEvent(Car car) throws SimulationNotInitializedException {
		this.car = car;
	}

	@Override
	public void processEvent() throws SimulationNotInitializedException, EventAlreadyExistsException {
		var durationInSeconds = (int)this.car.closeDoor();
		this.simulation.addSimEvent(durationInSeconds, new CarMoveSimEvent(this.car.getElevatorShaft()));
	}
}