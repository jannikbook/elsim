package main.java.elsim.models;
import java.util.ArrayList;

/**
 * Class for passengers that use elevators
 * @see Load
 * @see PassengerType
 * @author jdunker
 */
public class Passenger extends Load {
    public ArrayList<Item> items;
    private PassengerType type;
    private Floor floorStartingPoint;
    private Floor floorDestination;

    /**
     * Manual constructor for Passenger Objects
     * @param type Passenger type defined in enum
     * @param mass Mass of the passenger in kg
     * @param spaceRequired Area needed for the passenger in m²
     * @param floorStartingPoint Starting floor of passenger
     * @param floorDestination Destination floor of passenger
     */
    public Passenger(PassengerType type, int mass, double spaceRequired, Floor floorStartingPoint, Floor floorDestination){
        this.type = type;
        this.mass = mass;
        this.spaceRequired = spaceRequired;
        this.floorStartingPoint = floorStartingPoint;
        this.floorDestination = floorDestination;
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
}
