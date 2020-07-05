package main.java.elsim.models;

/**
 * An Item is a object that can be transported by a Passenger of an elevator.
 * This Class defines the common attributes like mass and required space
 * that are relevant for the simulation.
 * @see Load
 * @author ptomalak
 */
public class Item extends Load {
    static int UNKNOWN                  = 0;
    static int MIN_MASS                 = 1;
    static int MAX_MASS                 = 20;
    static int DECIMALS                 = 2;
    static double MIN_AREA              = 0.01;
    static double MAX_AREA              = 1;

    Passenger owner;

    /**
     * Manual constructor for Item Objects
     * @param owner Passenger that owns the item
     * @param mass Mass of the item in kg
     * @param spaceRequired Area needed for the item in m²
     */
    public Item (Passenger owner, int mass, double spaceRequired){
        this.owner = owner;
        this.mass = mass;
        this.spaceRequired = spaceRequired;
    }
    /**
     * Constructor for random Item Objects. An owner has to be provided,
     * while mass and required space get randomized.
     * @param owner Passenger that owns the item
     * @return Item with randomly generated attributes
     */
    public Item CreateRandomItem (Passenger owner){
        Item myItem = new Item(owner,UNKNOWN,UNKNOWN);
        myItem.mass = RNG.getInstance().getRandomInteger(MIN_MASS,MAX_MASS);
        myItem.spaceRequired = RNG.getInstance().getRandomDouble(MIN_AREA,MAX_AREA,DECIMALS);
        return myItem;
    }
}
