package main.java.elsim.models;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for a elevator car used by passengers
 * @author fwagner
 */

public class Car {

	private ElevatorShaft shaft;

	private int maxPassengerNumber;
	private int maxMass;							// Mass in kg
	private double maxCarArea;
	private double changeDoorTime;					// Time in seconds
	
	private int currentPassengerNumber;
	private int currentMass;						// Mass in kg
	private double currentCarArea;					// area in m²
	private List<Passenger> currentPassengers;		

	/**
     * Parameterized Creation of Car Object
	 * @param maxPassangerNumber Maximum amount of passenger inside a car
     * @param maxMass Maximum amount of mass inside a car in kg
     * @param maxCarArea Rectangle desribing the maximum car area
     * @param changeDoorTime Time it takes to open/close the door
     */
	public Car(int maxPassangerNumber, int maxMass, double maxCarArea, double changeDoorTime) {
		super(); // warum?!

		this.maxPassengerNumber = maxPassangerNumber;
		this.maxMass = maxMass;
		this.maxCarArea = maxCarArea;
		this.changeDoorTime = changeDoorTime;
		this.currentPassengerNumber = 0;
		this.currentMass = 0;
		this.currentCarArea = 0.0;
		this.currentPassengers = new ArrayList<>();
	}

	public void setElevatorShaft(ElevatorShaft shaft) {
		if (this.shaft == null) {
			this.shaft = shaft;
		}
	}

	/**
	 * Returns number of peeple in the car 
	 * @return Number of peeple in the car 
	 */
	public int getCurrentPersonNumber() {
		return currentPassengerNumber;
	}

	/**
	 * Returns current mass inside the car
	 * @return Current mass inside the car
	 */
	public int getCurrentMass() {
		return currentMass;
	}

	/**
	 * Returns current area used inside the car 
	 * @return Current area used inside the car 
	 */
	public double getCurrentCarArea() {
		return currentCarArea;
	}

	/**
	 * Returns list of passenger inside the car
	 * @return List of passenger inside the car
	 */
	public List<Passenger> getCurrentPassengers() {
		return currentPassengers;
	}
	
	/**
	 * Returns spare mass inside the car (maxMass - currentMass)
	 * @return Spare mass inside the car
	 */
	public int getSpareMass() {
		return this.maxMass - this.currentMass;
	}
	
	/**
	 * Returns spare area inside the car (maxAre - currentArea)
	 * @return Spare mass inside the car
	 */
	public double getSpareArea() {
		return this.maxCarArea - this.currentCarArea;
	}
	
	/**
	 * Returns spare passenger number (maxPassengerNumber - currentPassengerNumber)
	 * @return Spare passenger number
	 */
	public int getSparePassenger() {
		return this.maxPassengerNumber - this.currentPassengerNumber;
	}
	
	/**
     * Adding a passenger to a car with a check to the limits
     * @see Passenger
     * @param passenger Passenger to be added to the car
     * @return Status if passenger can be added
     */
	public boolean addPassenger(Passenger passenger) {
		int addedMass = passenger.getMass();
		double addedCarArea = passenger.getSpaceRequired();
		List<Item> passengerItems = passenger.getItems();
		
		for (int i = 0; i < passengerItems.size(); i++) {
			addedMass = addedMass + passengerItems.get(i).getMass();
			addedCarArea = passengerItems.get(i).getSpaceRequired();
		}
		
		this.currentPassengers.add(passenger);
		this.currentPassengerNumber++;
		this.currentMass = this.currentMass + addedMass;
		this.currentCarArea = this.currentCarArea + addedCarArea;
		return true;
		
	}
	
	/**
	 * Removing a passenger from the car
     * @see Passenger
     * @param passenger Passenger to be removed to the car
     */
	public void removePassenger(Passenger passenger) {
		
		int removedMass = passenger.getMass();
		double removedCarArea = passenger.getSpaceRequired();
		List<Item> passengerItems = passenger.getItems();
		
		for (int i = 0; i < passengerItems.size(); i++) {
			removedMass = removedMass + passengerItems.get(i).getMass();	
			removedCarArea = passengerItems.get(i).getSpaceRequired();
		}
		
		this.currentPassengers.remove(passenger);
		this.currentPassengerNumber--;
		this.currentMass = this.currentMass - removedMass;
		this.currentCarArea = currentCarArea - removedCarArea;
	}
	
	/**
	 * Opens the door
	 * @return changeDoorTime Time it takes to open the door 
	 */
	public double openDoor() {
		return changeDoorTime;
	}
	
	/**
	 * Closes the door
	 * @return changeDoorTime Time it takes to close the door 
	 */
	public double closeDoor() {
		return changeDoorTime;
	}

	public ElevatorShaft getElevatorShaft() {
		return this.shaft;
	}

	public Duration removeAllPassengersAtFloor(Floor floor) {
		Duration exitDuration = Duration.ZERO;

		for (var p : this.currentPassengers) {
			if (p.getFloorDestination().equals(floor)) {
				this.removePassenger(p);
				exitDuration = exitDuration.plus(p.getTimeChange());
			}
		}

		return exitDuration;
	}

	public Duration addAllPassengersAtFloor(Floor currentFloor) {
		var moveDirection = shaft.getDir();
		var nextPassenger = currentFloor.findAndRemoveNextPossiblePassenger(maxMass - currentMass, getSpareArea(), moveDirection);
		if (nextPassenger == null) {
			return Duration.ZERO;
		}

		Duration enterDuration = nextPassenger.getTimeChange();

		while (nextPassenger != null) {
			this.addPassenger(nextPassenger);
			enterDuration = enterDuration.plus(nextPassenger.getTimeChange());

			var freeMass = this.maxMass - this.currentMass;
			currentFloor.findAndRemoveNextPossiblePassenger(freeMass, getSpareArea(), moveDirection);
		}

		return enterDuration;
	}
}
