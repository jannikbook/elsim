package main.java.elsim.models;

import main.java.elsim.simulation.Simulation;
import main.java.elsim.simulation.SimulationNotInitializedException;
import main.java.elsim.simulation.events.PassengerArrivesAtFloorEvent;
import main.java.elsim.simulation.events.PassengerLeavesFloorSimEvent;

import java.time.Duration;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * Class for floors where the passengers wait for the elevator
 * @see Passenger
 * @author mjaeckel
 */

public class Floor {
    private static final Logger LOGGER = Logger.getLogger(Floor.class.getName());

    private int floorNumber;
    private int height;
    private LinkedList<Passenger> passengers;
    private boolean buttonPressedUp;
    private boolean buttonPressedDown;

    private final int passengerAmount;

    /**
     * Manual constructor for Passenger Objects
     * @param height Height of the floor in centimeters
     */
    public Floor(int floorNumber, int height, int minPassengers, int maxPassengers) {
        this.floorNumber = floorNumber;
        this.height = height;
        passengers = new LinkedList<>();

        if (minPassengers > maxPassengers) {
            throw new IllegalArgumentException("minPassengers has to be smaller than or equal to maxPassengers.");
        }

        this.passengerAmount = RNG.getInstance().getRandomInteger(minPassengers, maxPassengers);
    }

    /**
     * Get number of floor
     * @return the number of the floor
     */
    public int getFloorNumber() { return floorNumber; }

    public void loadPassengers(Simulation sim, ElevatorShaft shaft) throws SimulationNotInitializedException {

        var allFloors = shaft.getFloors();
        var secondsDuration = Duration.between(sim.getStart(), sim.getEnd()).toSeconds();

        for (int i = 0; i < this.passengerAmount; i++) {

            var targetFloorIndex = RNG.getInstance().getRandomIntegerExcept(0, allFloors.size() - 1, allFloors.indexOf(this));
            var p = new Passenger(this, allFloors.get(targetFloorIndex));
            var randomOffset = RNG.getInstance().getRandomInteger(0, (int)secondsDuration);

            sim.addSimEvent(randomOffset, new PassengerArrivesAtFloorEvent(p, this, shaft));
        }
    }

    /**
     * Add a passenger who wait at this floor for the elevator and press the button up or down to call the elevator car
     * @param passenger A new passenger who will wait for an elevator
     */
    public void addPassenger(Passenger passenger) {
        int destinationFloorNumber = passenger.getFloorDestination().getFloorNumber();

        if (destinationFloorNumber == this.floorNumber) {
            LOGGER.warning(String.format("Tried to add passenger to floor %d that has the same floor as their destination.", this.floorNumber));
        }

        passengers.add(passenger);
        if (destinationFloorNumber > this.floorNumber) {
            buttonPressedUp = true;
        }

        if (destinationFloorNumber < this.floorNumber) {
            buttonPressedDown = true;
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
            for (Item item : tmp.getItems()) {
                neededMass += item.getMass();
                neededSpace += item.getSpaceRequired();
            }

            if (neededMass <= freeMass &&
                neededSpace <= freeSpace && (
                    (direction == MoveDirection.Hold) ||
                    (direction == MoveDirection.Up && directionFloorNumber > floorNumber) ||
                    (direction == MoveDirection.Down && directionFloorNumber < floorNumber)
                )) {

                passenger = tmp;
                break;
            }
        }
        if (passenger != null) {
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

    public boolean removePassenger(Passenger passenger) {
        var pGoingUp = passenger.getFloorDestination().getFloorNumber() > this.getFloorNumber();

        // これは文書化する必要はありません - とにかく誰もそれを理解していません。
        if (pGoingUp) {
            boolean otherGoingUp = false;

            for (var p : this.passengers) {
                if (!p.equals(passenger) && p.getFloorDestination().getFloorNumber() > this.getFloorNumber()) {
                    otherGoingUp = true;
                }
            }

            if (!otherGoingUp) {
                this.resetButtonUp();
            }
        } else {
            boolean otherGoingDown = false;

            for (var p : this.passengers) {
                if (!p.equals(passenger) && p.getFloorDestination().getFloorNumber() < this.getFloorNumber()) {
                    otherGoingDown = true;
                }
            }

            if (!otherGoingDown) {
                this.resetButtonDown();
            }
        }

        var destNumber = passenger.getFloorDestination().getFloorNumber();
        return this.passengers.remove(passenger);
    }

    public void initPatienceEvents(Simulation sim) {
        for (var p : this.passengers) {
            try {
                sim.addSimEvent(p.getTimePatience(), new PassengerLeavesFloorSimEvent(this, p));
            }
            catch (SimulationNotInitializedException e) {}
        }
    }
}
