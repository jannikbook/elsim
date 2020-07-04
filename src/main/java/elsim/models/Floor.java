package main.java.elsim.models;

import main.java.elsim.simulation.EventAlreadyExistsException;
import main.java.elsim.simulation.Simulation;
import main.java.elsim.simulation.SimulationNotInitializedException;
import main.java.elsim.simulation.events.PassengerLeavesFloorSimEvent;

import java.util.LinkedList;

/**
 * Class for floors where the passengers wait for the elevator
 * @see Passenger
 * @author mjaeckel
 */

public class Floor {
    private int floorNumber;
    private int height;
    private LinkedList<Passenger> passengers;
    private boolean buttonPressedUp;
    private boolean buttonPressedDown;
    //private enum floorType; // Top or base floor do not have booth buttons

    /**
     * Manual constructor for Passenger Objects
     * @param  height Height of the floor in centimeters
     */
    public Floor(int floorNumber, int height) {
        this.floorNumber = floorNumber;
        this.height = height;
        passengers =
                new LinkedList<>();
    }

    /**
     * Get number of floor
     * @return the number of the floor
     */
    public int getFloorNumber() { return floorNumber; }

    /**
     * Add a passenger who wait at this floor for the elevator and press the button up or down to call the elevator car
     * @param passenger A new passenger who will wait for an elevator
     */
    public void addPassenger(Passenger passenger) throws SimulationNotInitializedException {
        int destinationFloorNumber = passenger.getFloorDestination().getFloorNumber();

        if (destinationFloorNumber == this.floorNumber) {
            // just skip this passenger, maybe log to WARN
        }

        passengers.add(passenger);
        if (destinationFloorNumber > this.floorNumber) {
            buttonPressedUp = true;
        }

        if (destinationFloorNumber < this.floorNumber) {
            buttonPressedDown = true;
        }

        var sim = Simulation.getInstance();
        try {
            sim.addSimEvent(passenger.getTimePatience(), new PassengerLeavesFloorSimEvent(this, passenger));
        }
        catch (EventAlreadyExistsException e) {
            // log to WARN
        }
    }

    /**
     * Get the height of the floor
     * @return Height of the floor as int
     */
    public int getHeight() { return height; }

    /**
     * Find, return and remove next waiting passenger who can get into the elevator limited by mass, required space and direction
     * @param freeMass Free mass of the elevator
     * @param freeSpace Free space of the elevator
     * @param direction The move direction of the elevator car
     * @return Passenger as type Passenger who fit with the conditions and can enter the elevator. If no passenger fit it will return null.
     */
    public Passenger findAndRemoveNextPossiblePassenger(int freeMass, double freeSpace, MoveDirection direction) {
        Passenger passenger = null;
        for (Passenger tmp: passengers) {
            int directionFloorNumber = tmp.getFloorDestination().getFloorNumber();
            int neededMass = tmp.getMass();
            double neededSpace = tmp.getSpaceRequired();
            // The needed mass and space for the passenger + his/her items
            for (Item item:tmp.getItems()) {
                neededMass += item.getMass();
                neededSpace += item.getSpaceRequired();
            }
            if(neededMass <= freeMass && neededSpace <= freeSpace && (
                            (direction == MoveDirection.Up && directionFloorNumber > floorNumber) ||
                            (direction == MoveDirection.Down && directionFloorNumber < floorNumber) ) ) {


                passenger = tmp;
                break;
            }
        }
        if(passenger != null) {
            passengers.remove(passenger);
        }
        return passenger;
    }

    /**
     * Getter, check if the up button was pressed
     * @return true if the button is pressed and false if not
     */
    public boolean getButtonPressedUp() { return buttonPressedUp; }

    /**
     * Reset the button up to false (necessary after the car arrived)
     */
    public void resetButtonUp() { buttonPressedUp = false; }

    /**
     * Getter, check if the down button was pressed
     * @return true if the button is pressed and false if not
     */
    public boolean getButtonPressedDown() { return buttonPressedDown; }

    /**
     * Reset the button down to false (necessary after the car arrived)
     */
    public void resetButtonDown() { buttonPressedDown = false; }

    public void removePassenger(Passenger passenger) {
        this.passengers.remove(passenger);
    }
}
