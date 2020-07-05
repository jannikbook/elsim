package main.java.elsim.models;

import main.java.elsim.config.ConfigManager;

/**
 * An Item is a object that can be transported by a Passenger of an elevator.
 * This Class defines the common attributes like mass and required space
 * that are relevant for the simulation.
 * @see Load
 * @author ptomalak
 */
public class Item extends Load {
    // minimal and maximal Mass of Items
    static int MIN_MASS     = ConfigManager.getInstance().getPropAsInt("Item.minMass");
    static int MAX_MASS     = ConfigManager.getInstance().getPropAsInt("Item.maxMass");
    // Always round to 2 decimal places
    static int DECIMALS     = 2;
    // minimal and maximal required Area for Items
    static double MIN_AREA  = ConfigManager.getInstance().getPropAsDouble("Item.minArea");
    static double MAX_AREA  = ConfigManager.getInstance().getPropAsDouble("Item.maxArea");

    /**
     * Manual constructor for Item Objects
     * @param mass Mass of the item in kg
     * @param spaceRequired Area needed for the item in m²
     */
    public Item (int mass, double spaceRequired){
        this.mass = mass;
        this.spaceRequired = spaceRequired;
    }
    /**
     * Constructor for Item Objects with randomized mass and required space.
     */
    public Item (){
        this.mass = RNG.getInstance().getRandomInteger(MIN_MASS,MAX_MASS);
        this.spaceRequired = RNG.getInstance().getRandomDouble(MIN_AREA,MAX_AREA,DECIMALS);
    }


}
