package main.java.elsim.models;
import main.java.elsim.config.ConfigManager;

import java.time.Duration;
import java.util.ArrayList;

/**
 * Class for passengers that use elevators
 * @see Load
 * @see Duration
 * @author jdunker
 */
public class Passenger extends Load {
    private Duration timeChange;
    private Duration timePatience;
    public ArrayList<Item> items;
    private Floor floorStartingPoint;
    private Floor floorDestination;

    /**
     * Manual constructor for Passenger Objects
     * @param mass Mass of the passenger in kg
     * @param spaceRequired Area needed for the passenger in m²
     * @param timeChange Duration for getting on or off the car
     * @param timePatience Patience of the passenger as Duration
     * @param floorStartingPoint Starting floor of passenger
     * @param floorDestination Destination floor of passenger
     */
    public Passenger(int mass, double spaceRequired, Duration timeChange, Duration timePatience, Floor floorStartingPoint, Floor floorDestination){
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
    public Passenger(Floor floorStartingPoint, Floor floorDestination) {
        this.floorStartingPoint = floorStartingPoint;
        this.floorDestination = floorDestination;

        String conf = "Passenger.people." + String.valueOf(RNG.getInstance().getRandomInteger(0, ConfigManager.getInstance().getPropAsInt("Passenger.people.length")));
        String[] vars = ConfigManager.getInstance().getProp(conf).split(";");
        String[] varsMin = new String[vars.length];
        String[] varsMax = new String[vars.length];
        for (int i = 0; i < vars.length; i++) {
            if (vars[i].contains("..")) {
                varsMin[i] = vars[i].split("\\.\\.")[0];
                varsMax[i] = vars[i].split("\\.\\.")[1];
            } else {
                varsMin[i] = vars[i];
            varsMax[i] = vars[i];
            }
        }

        this.mass = RNG.getInstance().getRandomInteger(Integer.parseInt(varsMin[0]), Integer.parseInt(varsMax[0]));
        this.spaceRequired = RNG.getInstance().getRandomDouble(Double.parseDouble(varsMin[1]), Double.parseDouble(varsMax[1]),2);
        this.timeChange = Duration.ofMillis((long) RNG.getInstance().getRandomInteger(Integer.parseInt(varsMin[2]), Integer.parseInt(varsMax[2])));
        this.timePatience = Duration.ofMillis((long) RNG.getInstance().getRandomInteger(Integer.parseInt(varsMin[3]), Integer.parseInt(varsMax[3])));

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
     * @return Duration object
     */
    public Duration getTimeChange() {
        return timeChange;
    }

    /**
     * Set time required to get in or out of the elevator
     * @param timeChange Duration object
     */
    public void setTimeChange(Duration timeChange) {
        this.timeChange = timeChange;
    }

    /**
     * Get patience of passenger
     * @return time
     */
    public Duration getTimePatience() {
        return timePatience;
    }

    /**
     * Set patience of passenger
     * @param timePatience time
     */
    public void setTimePatience(Duration timePatience) {
        this.timePatience = timePatience;
    }
}
