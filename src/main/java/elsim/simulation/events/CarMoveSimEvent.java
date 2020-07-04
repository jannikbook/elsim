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
		var duration = this.shaft.moveCar();
		if (duration.isZero()) {
			return; // no next floor to move to
		}

		var elevatorCar = this.shaft.getElevatorCar();
		this.simulation.addSimEvent(duration, new DoorOpenSimEvent(elevatorCar));
	}
}
