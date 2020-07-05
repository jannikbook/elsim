package main.java.elsim.simulation.events;

import main.java.elsim.models.ElevatorShaft;
import main.java.elsim.models.MoveDirection;
import main.java.elsim.simulation.EventAlreadyExistsException;
import main.java.elsim.simulation.SimulationNotInitializedException;
import java.util.logging.Logger;

/**
 * Describes the elevator car moving to the next floor.
 */
public class CarMoveSimEvent extends AbstractSimEvent {
	private static final Logger LOGGER = Logger.getLogger(CarMoveSimEvent.class.getName());

	private final ElevatorShaft shaft;

	public CarMoveSimEvent(ElevatorShaft shaft) throws SimulationNotInitializedException {
		this.shaft = shaft;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processEvent() throws SimulationNotInitializedException, EventAlreadyExistsException {
		var duration = this.shaft.moveCar();
		LOGGER.fine(String.format("Elevator arrives at floor %d with %d passengers.",
				shaft.getCurrentCarFloor().getFloorNumber(),
				shaft.getElevatorCar().getCurrentPassengers().size()));

		if (duration.isZero()) {
			return; // no next floor to move to
		}

		var elevatorCar = this.shaft.getElevatorCar();
		this.simulation.addSimEvent(duration, new DoorOpenSimEvent(elevatorCar));
	}
}
