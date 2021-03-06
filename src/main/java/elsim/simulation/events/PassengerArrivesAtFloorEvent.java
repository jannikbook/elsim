package main.java.elsim.simulation.events;

import main.java.elsim.models.ElevatorShaft;
import main.java.elsim.models.Floor;
import main.java.elsim.models.MoveDirection;
import main.java.elsim.models.Passenger;
import main.java.elsim.simulation.SimulationNotInitializedException;

import java.util.logging.Logger;

public class PassengerArrivesAtFloorEvent extends AbstractSimEvent {
	private static final Logger LOGGER = Logger.getLogger(PassengerArrivesAtFloorEvent.class.getName());

	private final Passenger passenger;
	private final Floor floor;
	private final ElevatorShaft shaft;


	/**
	 * Create an event that should be processed at a specific time during the simulation.
	 *
	 * @throws SimulationNotInitializedException When the simulation has not been initialized yet.
	 */
	public PassengerArrivesAtFloorEvent(Passenger passenger, Floor floor, ElevatorShaft shaft) throws SimulationNotInitializedException {
		this.passenger = passenger;
		this.floor = floor;
		this.shaft = shaft;
	}

	@Override
	public void processEvent() throws SimulationNotInitializedException {
		this.floor.addPassenger(passenger);
		LOGGER.fine(String.format("Passenger arrives at floor %d, wanting to go to floor %d. (at %s)",
				this.floor.getFloorNumber(),
				passenger.getFloorDestination().getFloorNumber(),
				this.getNowFormatted()));

		this.simulation.addSimEvent(passenger.getTimePatience(), new PassengerLeavesFloorSimEvent(this.floor, passenger));

		if (this.shaft.getDir() == MoveDirection.Hold) {
			if (this.shaft.getCurrentCarFloor().getFloorNumber() == passenger.getFloorStartingPoint().getFloorNumber()) {
				this.simulation.addSimEvent(0, new DoorOpenSimEvent(this.shaft.getElevatorCar()));
			} else {
				this.simulation.addSimEvent(0, new CarMoveSimEvent(this.shaft));
			}
		}
	}
}
