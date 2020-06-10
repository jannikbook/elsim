package main.java.elsim.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for a elevator car used by passengers
 * @author fwagner
 */
public class Car {

	public enum movement {
		UP,
		DOWN,
		STILL
	}
	
	private int maxPassengerNumber;
	private int maxMass;							// Mass in kg
	private Rectangle maxCarArea;					// DEBATABLE (currently custom rectangle)
	private int elevatorSpeed;						// Unit for speed = m/s
	private int numberOfFloors;
	
	private int currentPassengerNumber;
	private int currentMass;						// Mass in kg
	private double currentCarArea;					// DEBATABLE (currently area in m²)
	private int currentFloor;						// Bottom terminal floor = 1
	private movement currentMovement;				// Movement of the car
	private List<Passenger> currentPassengers;		

	/**
     * Parameterized Creation of Car Object
     * @param maxPassangerNumber Maximum amount of passenger inside a car
     * @param maxMass Maximum amount of mass inside a car in kg
     * @param maxCarArea Rectangle desribing the maximum car area
     * @param elevatorSpeed Speed of a car while switching floors in m/s
     * @param numberOfFloors Number of floors which the car can reach
     */
	public Car(int maxPassangerNumber, int maxMass, Rectangle maxCarArea, int elevatorSpeed, int numberOfFloors) {
		super();
		this.maxPassengerNumber = maxPassangerNumber;
		this.maxMass = maxMass;
		this.maxCarArea = maxCarArea;
		this.elevatorSpeed = elevatorSpeed;
		this.numberOfFloors = numberOfFloors;
		this.currentPassengerNumber = 0;
		this.currentMass = 0;
		this.currentCarArea = 0.0;
		this.currentFloor = 1;
		this.currentMovement = movement.STILL;
		this.currentPassengers = new ArrayList<>();
	}
	
	/**
	 * Returns speed of the elevator (m/s) 
	 * @return Speed of the elevator
	 */
	public int getElevatorSpeed() {
		return elevatorSpeed;
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
	 * Returns current floor number
	 * @return Current floor number
	 */
	public int getCurrentFloor() {
		return currentFloor;
	}
	
	/**
	 * Returns movement of the elevator (enum)
	 * @return Movement of the elevator
	 */
	public movement getCurrentMovement() {
		return currentMovement;
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
		return this.maxCarArea.getArea() - this.currentCarArea;
	}
	
	/**
     * Adding a passenger to a car with a check to the limits
     * @see Passenger
     * @param passenger Passenger to be added to the car
     * @return Status if passenger can be added
     */
	public boolean addPassenger(Passenger passenger) {
		int checkPersonNumber = this.currentPassengerNumber + 1;
		int checkMass = this.currentMass + passenger.getMass();
		double checkCarArea = 0.00;
		List<Item> passengerItems = passenger.getItems();
		
		for (int i = 0; i < passengerItems.size(); i++) {
			checkMass = checkMass + passengerItems.get(i).mass;
			checkCarArea = passengerItems.get(i).spaceRequired;
		}
		
		if(checkPersonNumber > this.maxPassengerNumber) {
			System.out.println("Passenger number exceeds limit");
			return false;
		}
		
		if(checkMass > this.maxMass) {
			System.out.println("Mass exceeds the limit");
			return false;
		}
		
		if(checkCarArea > this.maxCarArea.getArea()) {
			System.out.println("Car area exceeds the limit");
		}
		
		this.currentPassengers.add(passenger);
		this.currentPassengerNumber = checkPersonNumber;
		this.currentMass = checkMass;
		this.currentCarArea = checkCarArea;
		return true;
		
	}
	
	/**
	 * Removing a passenger from the car
     * @see Passenger
     * @param passenger Passenger to be removed to the car
     */
	public void removePassenger(Passenger passenger) {
		
		int removedMass = passenger.getMass();
		double removedCarArea = 0.0;
		List<Item> passengerItems = passenger.getItems();
		
		for (int i = 0; i < passengerItems.size(); i++) {
			removedMass = removedMass + passengerItems.get(i).mass;	
			removedCarArea = passengerItems.get(i).spaceRequired;
		}
		
		this.currentPassengers.remove(passenger);
		this.currentPassengerNumber = this.currentPassengerNumber - 1;
		this.currentMass = this.currentMass - removedMass;
		this.currentCarArea = currentCarArea - removedCarArea;
	}
	
}
