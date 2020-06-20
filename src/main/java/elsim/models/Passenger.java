package main.java.elsim.models;
import java.util.ArrayList;

/**
 * Class for passengers that use elevators
 * @see Load
 * @author jdunker
 */
public class Passenger extends Load {
    private int timeChange;
    private int timePatience;
    public ArrayList<Item> items;
    private Floor floorStartingPoint;
    private Floor floorDestination;

    /**
     * Manual constructor for Passenger Objects
     * @param mass Mass of the passenger in kg
     * @param spaceRequired Area needed for the passenger in m²
     * @param floorStartingPoint Starting floor of passenger
     * @param floorDestination Destination floor of passenger
     */
    public Passenger(int mass, double spaceRequired, int timeChange, int timePatience, Floor floorStartingPoint, Floor floorDestination){
        this.mass = mass;
        this.timeChange = timeChange;
        this.timePatience = timePatience;
        this.spaceRequired = spaceRequired;
        this.floorStartingPoint = floorStartingPoint;
        this.floorDestination = floorDestination;
    }

    /**
     * Simpler constructor for Passenger objects
     * @param floorStartingPoint Starting floor of passenger
     * @param floorDestination Destination floor of passenger
     */
    public Passenger(Floor floorStartingPoint, Floor floorDestination){
        this(80, 0.25, 2500, 3600, floorStartingPoint, floorDestination);
    }

    /**
     * Get a passenger's list of items
     * @return ArrayList of items
     */
    public ArrayList<Item> getItems(){
        return this.items;
    }

    /**
     * Get starting floor
     * @return Floor object
     */
    public Floor getFloorStartingPoint() {
        return floorStartingPoint;
    }

    /**
     * Get destination floor
     * @return Floor object
     */
    public Floor getFloorDestination() {
        return floorDestination;
    }

    /**
     * Get time required to get in or out of the elevator
     * @return time in ms
     */
    public int getTimeChange() {
        return timeChange;
    }

    /**
     * Set time required to get in or out of the elevator
     * @param timeChange time in ms
     */
    public void setTimeChange(int timeChange) {
        this.timeChange = timeChange;
    }

    /**
     * Get patience of passenger
     * @return time in ms
     */
    public int getTimePatience() {
        return timePatience;
    }

    /**
     * Set patience of passenger
     * @param timePatience time in ms
     */
    public void setTimePatience(int timePatience) {
        this.timePatience = timePatience;
    }
}
