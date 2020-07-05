package main.java.elsim.models;

import main.java.elsim.config.ConfigManager;

import java.util.Random;

/**
 * A simple random number generator, to provide randomized values for different purposes throughout the simulation
 * @author ptomalak, fwagner
 */
public class RNG {
		
	private int seed;
	private Random randomGenerator;
	
	/**
	 * InstanceHolder of RNG Class (Singleton Usage)
	 */
	private static final class InstanceHolder {
		static final RNG INSTANCE = new RNG();
	}
	
	/**
	 * Private Constructor (only to be used by Singleton)
	 */
	private RNG() {
		this.seed = ConfigManager.getInstance().getPropAsInt("RNG.seed", 0);
		this.randomGenerator = new Random(seed);
	}
	
	/**
	 * Static method to return the defined RNG instance
	 * @return RNG Instance of RNG class which was created
	 */
	public static RNG getInstance() {
		return InstanceHolder.INSTANCE;
	}

	/**
	 * Getter of seed
	 * @return seed Seed which is defined in the config to be used for the generator
	 */
	public int getSeed() {
		return seed;
	}
	
    /**
     * Generates a random integer value between given boundaries
 	 * @param min The minimal possible outcome of the randomization
 	 * @param max The maximal possible outcome of the randomization
     * @return A random integer
     */
    public int getRandomInteger(int min, int max) {
    	return this.randomGenerator.nextInt((max-min) + 1) + min;
    }

	/**
	 * Generates a random integer value between given boundaries with the exception of a given number.
	 * @param min The minimal possible outcome of the randomization
	 * @param max The maximal possible outcome of the randomization
	 * @param except An integer which will be excluded from the possible return values.
	 * @return A random integer between the given boundaries except for the given number.
	 */
	public int getRandomIntegerExcept(int min, int max, int except) {
    	int random = getRandomInteger(min, max - 1);
    	return random < except ? random : random + 1;
    }

    /**
     * Generates a random double value between given boundaries and rounds it to a given number of decimals
     * @param min The minimal possible outcome of the randomization
     * @param max The maximal possible outcome of the randomization
     * @param decimals The amount of decimal places the result shall be rounded to
     * @return A random double, rounded to the given decimal places
     */
    public double getRandomDouble(double min, double max, int decimals) {
        double randomNumber;
        randomNumber = min + (max - min) * this.randomGenerator.nextDouble();
        return (double)Math.round(randomNumber * Math.pow(10,decimals)) / Math.pow(10,decimals);
    }
}
