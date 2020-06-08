package main.java.elsim.models;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Class for floors where the passengers wait for the elevator
 * @see Passenger
 * @author mjaeckel
 */

public class Floor {
    private int height;
    private LinkedList<Passenger> passengers;

    /**
     * Manual constructor for Passenger Objects
     * @param  height Height of the floor in centimeters
     */
    public Floor(int height) {
        this.height = height;
        passengers = new LinkedList<>();
    }

    /**
     * Add a passenger who wait at this floor for the elevator
     * @param passenger A new passenger who will wait for an elevator
     */
    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
    }

    /**
     * Get the height of the floor
     * @return Height of the floor as int
     */
    public int getHeight() { return height; }

    /**
     * Get the first waiting passenger
     * @return Passenger
     */
    public Passenger getFirstPassenger() { return passengers.peekFirst(); }

    /**
     * Get next waiting passenger who can get into the elevator limited by mass and required space
     * @param freeMass Free mass of the elevator
     * @param freeSpace Free space of the elevator
     * @return Passenger as type Passenger who fit with the conditions and can enter the elevator. If no passenger fit it will return null.
     */
    public Passenger getNextPossiblePassenger(int freeMass, double freeSpace) {
        for (Passenger passenger: passengers) {
            if(passenger.mass <= freeMass && passenger.spaceRequired <= freeSpace) {
                return passenger;
            }
        }
        return null;
    }
}
