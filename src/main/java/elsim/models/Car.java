package main.java.elsim.models;

import java.awt.Rectangle;
import java.util.List;

/**
 * Class for a elevator car used by passengers
 * @author fwagner
 */
public class Car {

	private int maxPersonNumber;
	// Mass in kg
	private int maxMass;
	private Rectangle maxCarArea;
	// Unit for speed = m/s
	private int elevatorSpeed;
	private int numberOfFloors;
	
	private int currentPersonNumber;
	private int currentMass;
	private Rectangle currentCarArea;
	private int currentFloor;
	
	/**
     * Parameterized Creation of Car Object
     * @param maxPersonNumber Maximum amount of passenger inside a car
     * @param maxMax Maximum amount of mass inside a car in kg
     * @param maxCarArea Rectangle desribing the maximum car area
     * @param elevatorSpeed Speed of a car while switching floors in m/s
     * @param numberOfFloors Number of floors which the car can reach
     */
	public Car(int maxPersonNumber, int maxMass, Rectangle maxCarArea, int elevatorSpeed, int numberOfFloors) {
		super();
		this.maxPersonNumber = maxPersonNumber;
		this.maxMass = maxMass;
		this.maxCarArea = maxCarArea;
		this.elevatorSpeed = elevatorSpeed;
		this.numberOfFloors = numberOfFloors;
		this.currentPersonNumber = 0;
		this.currentMass = 0;
		this.currentCarArea = new Rectangle(0, 0);
		this.currentFloor = 0;
	}
	
	/**
     * Adding a passenger to a car with a check to the limits
     * @see Passenger
     * @param passenger Passenger to be added to the car
     * @return boolean Status if passenger can be added
     */
	public boolean addPassenger(Passenger passenger) {
		int checkPersonNumber = this.currentPersonNumber + 1;
		int checkMass = this.currentMass + passenger.getMass();
		double checkCarAreaWidth = 0.00;
		double checkCarAreaHeight = 0.00;		
		List<Item> passengerItems = passenger.getItems();
		
		for (int i = 0; i < passengerItems.size(); i++) {
			checkMass = checkMass + passengerItems.get(i).mass;	
			checkCarAreaWidth = passengerItems.get(i).spaceRequired.getWidth();
			checkCarAreaHeight = passengerItems.get(i).spaceRequired.getHeight();
		}
		
		if(checkPersonNumber > this.maxPersonNumber) {
			System.out.println("Passenger number exceeds limit");
			return false;
		}
		
		if(checkMass > this.maxMass) {
			System.out.println("Mass exceeds the limit");
			return false;
		}
		
		if(checkCarAreaWidth > this.maxCarArea.getWidth()) {
			System.out.println("Car width exceeds the limit");
		}
		
		if(checkCarAreaHeight > this.maxCarArea.getHeight()) {
			System.out.println("Car height exceeds the limit");
		}
		
		this.currentPersonNumber = checkPersonNumber;
		this.currentMass = checkMass;
		this.currentCarArea = new Rectangle((int)checkCarAreaWidth, (int)checkCarAreaHeight);
		return true;
		
	}
	
	/**
	 * Removing a passenger from the car
     * @see Passenger
     * @param passenger Passenger to be removed to the car
     */
	public void removePassenger(Passenger passenger) {
		
		int removedMass = 0;
		double removedCarAreaWidth = 0.00;
		double removedCarAreaHeight = 0.00;		
		List<Item> passengerItems = passenger.getItems();
		
		for (int i = 0; i < passengerItems.size(); i++) {
			removedMass = removedMass + passengerItems.get(i).mass;	
			removedCarAreaWidth = passengerItems.get(i).spaceRequired.getWidth();
			removedCarAreaHeight = passengerItems.get(i).spaceRequired.getHeight();
		}
		
		this.currentPersonNumber = this.currentPersonNumber - 1;
		this.currentMass = this.currentMass - removedMass;
		this.currentCarArea = new Rectangle(
			(int)(this.currentCarArea.getWidth() - removedCarAreaWidth),
			(int)(this.currentCarArea.getHeight() - removedCarAreaHeight)
		);
	}
	
}
