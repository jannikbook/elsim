package main.java.elsim.simulation.events;

import main.java.elsim.models.Floor;
import main.java.elsim.models.Passenger;
import main.java.elsim.simulation.Simulation;
import main.java.elsim.simulation.SimulationNotInitializedException;

public class PassengerLeavesFloorSimEvent extends AbstractSimEvent {
	private final Floor floor;
	private final Passenger passenger;

	public PassengerLeavesFloorSimEvent(Floor floor, Passenger passenger) throws SimulationNotInitializedException {
		this.floor = floor;
		this.passenger = passenger;
	}

	@Override
	public void processEvent() throws SimulationNotInitializedException {
		this.floor.removePassenger(passenger);
	}
}
