package main.java.elsim.models;

/**
 * A simple random number generator, to provide randomized values for different purposes throughout the simulation
 * @author ptomalak
 */
public class RNG {
    /**
     * Generates a random integer value between given boundaries
 	 * @param min The minimal possible outcome of the randomization
 	 * @param max The maximal possible outcome of the randomization
     * @return A random integer
     */
    public static int getRandomInteger(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    /**
     * Generates a random double value between given boundaries and rounds it to a given number of decimals
     * @param min The minimal possible outcome of the randomization
     * @param max The maximal possible outcome of the randomization
     * @param decimals The amount of decimal places the result shall be rounded to
     * @return A random double, rounded to the given decimal places
     */
    public static double getRandomDouble(double min, double max, int decimals) {
        double randomNumber;
        randomNumber = ((Math.random() * (max - min)) + min);
        return  (double)Math.round(randomNumber * Math.pow(10,decimals)) / Math.pow(10,decimals);
    }
}
