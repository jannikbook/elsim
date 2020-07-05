package main.java.elsim.simulation.events;

import main.java.elsim.models.Floor;
import main.java.elsim.models.Passenger;
import main.java.elsim.simulation.SimulationNotInitializedException;

import java.util.logging.Logger;

/**
 * Describes a passenger's patience running out and leaving.
 */
public class PassengerLeavesFloorSimEvent extends AbstractSimEvent {
	private static final Logger LOGGER = Logger.getLogger(PassengerLeavesFloorSimEvent.class.getName());

	private final Floor floor;
	private final Passenger passenger;

	public PassengerLeavesFloorSimEvent(Floor floor, Passenger passenger) throws SimulationNotInitializedException {
		this.floor = floor;
		this.passenger = passenger;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processEvent() {
		if (this.floor.removePassenger(passenger)) {
			LOGGER.info(String.format("One passenger runs out of patience after they have waited for %d seconds", passenger.getTimePatience().toSeconds()));
		}
	}
}
