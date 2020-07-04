package main.java.elsim.simulation.events;

import main.java.elsim.models.Car;
import main.java.elsim.models.ElevatorShaft;
import main.java.elsim.simulation.EventAlreadyExistsException;
import main.java.elsim.simulation.SimulationNotInitializedException;

public class CarMoveSimEvent extends AbstractSimEvent {
	private final ElevatorShaft shaft;

	public CarMoveSimEvent(ElevatorShaft shaft) throws SimulationNotInitializedException {
		this.shaft = shaft;
	}

	@Override
	public void processEvent() throws SimulationNotInitializedException, EventAlreadyExistsException {
		var durationInSeconds = this.shaft.moveCar();
		var elevatorCar = this.shaft.getElevatorCar();
		this.simulation.addSimEvent(durationInSeconds, new DoorOpenSimEvent(elevatorCar));
	}
}
