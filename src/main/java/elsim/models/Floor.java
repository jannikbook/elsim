package main.java.elsim.models;

import java.util.LinkedList;

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
     * Remove the passenger at index X
     */
    public void removeFirstPassenger() {
        if(passengers.size() > 0)
            passengers.pop();
    }

    /**
     * Get the first waiting passenger
     * @return Passenger
     */
    public Passenger getFirstPassenger() { return passengers.peekFirst(); }

    /**
     * Find, return and remove next waiting passenger who can get into the elevator limited by mass and required space
     * @param freeMass Free mass of the elevator
     * @param freeSpace Free space of the elevator
     * @return Passenger as type Passenger who fit with the conditions and can enter the elevator. If no passenger fit it will return null.
     */
    public Passenger findAndRemoveNextPossiblePassenger(int freeMass, double freeSpace) {
        Passenger passenger = null;
        for (Passenger tmp: passengers) {
            if(tmp.mass <= freeMass && tmp.spaceRequired <= freeSpace) {
                passenger = tmp;
                break;
            }
        }
        if(passenger != null) {
            passengers.remove(passenger);
        }
        return passenger;
    }
}
