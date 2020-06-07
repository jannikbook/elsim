package main.java.elsim.models;

/**
 * A load is a object that can be transported by elevator such as items or passengers.
 * This abstract class defines the common attributes and mass and area and their respective getter methods
 * relevant for the simulation.
 * @see Item
 * @see Passenger
 * @author jdunker
 */
public abstract class Load {
    int mass;
    double area;

    /**
     * Get the mass of the object
     * @return mass in kg
     */
    public int getMass() {
        return mass;
    }

    /**
     * Get the required area for the object
     * @return area in m²
     */
    public double getArea() {
        return area;
    }
}
