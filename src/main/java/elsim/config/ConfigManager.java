package main.java.elsim.config;

import main.java.elsim.simulation.Simulation;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton class for managing config that wraps the properties api.
 * Includes methods to read, write and apply default config and set or get values.
 * Beware that the properties api uses only Strings internally and types are converted by this class.
 *
 * How to use:
 * Init and Exit
 * Use ConfigManager.getInstance().readConfig(); to load the configuration.
 * Default config will automatically be created if no file is present.
 * Use ConfigManager.getInstance().writeConfig(); to save the config to disk.
 * You can optionally pass a file name/path to use
 *
 * Get Config:
 * Use ConfigManager.getInstance().getProp("ConfigManager.details.version"); to get a String
 * Use ConfigManager.getInstance().getPropAsDouble("ConfigManager.details.version"); to get a Double
 * Use ConfigManager.getInstance().getProp("ConfigManager.details.version", "1.0"); to get a String and use "1.0" as default if nothing is set
 *
 * Set Config:
 * Use ConfigManager.getInstance().setProp("ConfigManager.details.version", 1.0)
 *
 * Default Config:
 * Either edit this class, method setDefaultConfig() or pass the parameter defaultValue in each getProp call that will be returned if no value is set
 * @author jdunker
 */
public class ConfigManager {
    private static final Logger LOGGER = Logger.getLogger(ConfigManager.class.getName());

    private Properties prop;
    private static ConfigManager instance;

    private ConfigManager() {
        this.prop = new SortedProperties();
    }

    /**
     * Get global instance of the ConfigManager
     * @return instance
     */
    public static ConfigManager getInstance() {
        if (ConfigManager.instance == null) {
            ConfigManager.instance = new ConfigManager();
        }
        return ConfigManager.instance;
    }
    /**
     * Apply default config
     */
    public void setDefaultConfig() {
        // Use this to add your own default config
        // or use the optional defaultValue parameter in the getter-methods instead
        LOGGER.info("[ConfigManager] Applying default config...");
        this.prop = new SortedProperties(); // empty config

        // Passenger namespace
        this.setProp("Passenger.people.length", 4);
        this.setProp("Passenger.people.0", "50..70;0.20..0.25;500..1500;40000..80000");
        this.setProp("Passenger.people.1", "75..100;0.30..0.40;500..2000;40000..80000");
        this.setProp("Passenger.people.2", "30..50;0.15..0.25;500..2000;40000..80000");
        this.setProp("Passenger.people.3", "50..100;0.20..0.40;500..2000;40000..80000");

        // RNG namespace
        this.setProp("RNG.seed", 12345678);

        // ElevatorCar namespace
        this.setProp("ElevatorCar.maxPassengerNumber", 12);
        this.setProp("ElevatorCar.maxCarArea", 9);
        this.setProp("ElevatorCar.maxMass", 1000);
        this.setProp("ElevatorCar.changeDoorTime", 1);

        // ElevatorShaft namespace
        this.setProp("ElevatorShaft.carSpeed",8);
        this.setProp("ElevatorShaft.floors.length", 8);
        this.setProp("ElevatorShaft.floors.0", "-1;500;0..5");
        this.setProp("ElevatorShaft.floors.1", "0;350;10..25");
        this.setProp("ElevatorShaft.floors.2", "1;300;5..10");
        this.setProp("ElevatorShaft.floors.3", "2;300;4..12");
        this.setProp("ElevatorShaft.floors.4", "3;400;2..5");
        this.setProp("ElevatorShaft.floors.5", "4;400;2..5");
        this.setProp("ElevatorShaft.floors.6", "5;382;15..20");
        this.setProp("ElevatorShaft.floors.7", "6;260;2..5");

        //Item namespace
        this.setProp("Item.minMass",1);
        this.setProp("Item.maxMass",20);
        this.setProp("Item.minArea",0.01);
        this.setProp("Item.maxArea",1);

        
        LOGGER.info("[ConfigManager] Applying default config... Done.");
    }

    /**
     * Get default file name of the config file
     * @return file name string
     */
    private String getDefaultFileName(){
        return "elsim.config";
    }

    /**
     * List config
     */
    public void listConfig() {
        List<String> keys = new ArrayList<String>();
        String out = "[ConfigManager]";
        for(String key : this.prop.stringPropertyNames()) {
            keys.add(key);
        }
        Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++){
            out += "\n" + keys.get(i) + "=" + getProp(keys.get(i), "<error retrieving prop>");
        }
        LOGGER.config(out);
    }
    /**
     * Read config from disk using default file name
     * If the config file can't be found or read the default config is used and a write to disk will be attempted.
     */
    public void readConfig() {
        this.readConfig(getDefaultFileName());
    }

    /**
     * Read config from disk
     * If the config file can't be found or read the default config is used and a write to disk will be attempted.
     * @param fileName Config file name, path is optional
     */
    public void readConfig(String fileName) {
        boolean skip = false;
        //if anything goes wrong, write a new config file and skip the rest of the read process

        LOGGER.info("[ConfigManager] Initializing config...");
        InputStream is = null;
        // first, apply the default config
        this.setDefaultConfig();
        LOGGER.info("[ConfigManager] Trying to apply config from file '" + fileName + "'...");
        try {
            // second, open file
            is = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            LOGGER.severe("[ConfigManager] File '" + fileName + "' not found. Creating a new config file instead.");
            this.writeConfig(fileName);
            skip = true;
        }
        if (!skip){
        try {
            // third, parse file
            this.prop.load(is);
        } catch (IOException e) {
            LOGGER.severe("[ConfigManager] File '" + fileName + "' is not readable (may be invalid). Attempting to create a new config file instead.");
            this.writeConfig(fileName);
            skip = true;
        }}
        if (!skip) {
            // everything went well
            LOGGER.info("[ConfigManager] Trying to apply config from file '" + fileName + "'... Done.");
        }
        LOGGER.info("[ConfigManager] Using the following config:");
        listConfig();
        LOGGER.info("[ConfigManager] Initializing config... Done.");
    }


    /**
     * Write config to disk using default file name
     */
    public void writeConfig() {
        this.writeConfig(getDefaultFileName());
    }

    /**
     * Write config to disk
     * @param fileName Config file name, path is optional
     */
    private void writeConfig(String fileName) {
        LOGGER.info("[ConfigManager] Writing config to file '" + fileName + "'...");
        try {
            this.prop.store(new FileOutputStream(fileName), null);
        } catch (IOException e) {
            LOGGER.severe("[ConfigManager] Could not write file '" + fileName + "'. ");
        }
        LOGGER.info("[ConfigManager] Writing config to file '" + fileName + "'... Done.");
    }

    // BELOW: Prop Setter Methods
    // BELOW THAT: Prop Getter Methods, EOF
    /**
     * Set a property to a value. Can be used with String, int, float, double or boolean values.
     * @param key e.g. "ConfigManager.details.name"
     * @param value e.g. "MyConfigManager"
     */
    public void setProp(String key, String value){
        this.prop.setProperty(key, value);
    }
    /**
     * Set a property to a value. Can be used with String, int, float, double or boolean values.
     * @param key e.g. "ConfigManager.details.version"
     * @param value e.g. 1
     */
    public void setProp(String key, int value){
        this.prop.setProperty(key, String.valueOf(value));
    }
    /**
     * Set a property to a value. Can be used with String, int, float, double or boolean values.
     * @param key e.g. "ConfigManager.details.version"
     * @param value e.g. 2.5
     */
    public void setProp(String key, float value){
        this.prop.setProperty(key, String.valueOf(value));
    }
    /**
     * Set a property to a value. Can be used with String, int, float, double or boolean values.
     * @param key e.g. "ConfigManager.details.version"
     * @param value e.g. 2.5
     */
    public void setProp(String key, double value){
        this.prop.setProperty(key, String.valueOf(value));
    }
    /**
     * Set a property to a value. Can be used with String, int, float, double or boolean values.
     * @param key e.g. "ConfigManager.details.booleansWork"
     * @param value e.g. true
     */
    public void setProp(String key, boolean value){
        this.prop.setProperty(key, String.valueOf(value));
    }

    // BELOW: Prop Getter Methods, EOF
    /**
     * Get value from a property. Use AsType methods as required.
     * You can specify a default as parameter or in the ConfigManager class.
     * @param key e.g. "ConfigManager.details.version"
     * @return value
     */
    public String getProp(String key){
        return this.prop.getProperty(key);
    }

    /**
     * Get value from a property. Use AsType methods as required.
     * You can specify a default as parameter or in the ConfigManager class.
     * @param key e.g. "ConfigManager.details.version"
     * @param defaultValue e.g. "1.0"
     * @return value
     */
    public String getProp(String key, String defaultValue){
        return this.prop.getProperty(key, defaultValue);
    }

    /**
     * Get value from a property. Use AsType methods as required.
     * You can specify a default as parameter or in the ConfigManager class.
     * @param key e.g. "ConfigManager.details.version"
     * @return value
     */
    public int getPropAsInt(String key){
        return Integer.parseInt(this.getProp(key));
    }

    /**
     * Get value from a property. Use AsType methods as required.
     * You can specify a default as parameter or in the ConfigManager class.
     * @param key e.g. "ConfigManager.details.version"
     * @param defaultValue e.g. 4
     * @return value
     */
    public int getPropAsInt(String key, int defaultValue){
        return Integer.parseInt(this.getProp(key, String.valueOf(defaultValue)));
    }

    /**
     * Get value from a property. Use AsType methods as required.
     * You can specify a default as parameter or in the ConfigManager class.
     * @param key e.g. "ConfigManager.details.version"
     * @return value
     */
    public float getPropAsFloat(String key){
        return Float.parseFloat(this.getProp(key));
    }

    /**
     * Get value from a property. Use AsType methods as required.
     * You can specify a default as parameter or in the ConfigManager class.
     * @param key e.g. "ConfigManager.details.version"
     * @param defaultValue e.g. 1.0
     * @return value
     */
    public float getPropAsFloat(String key, float defaultValue){
        return Float.parseFloat(this.getProp(key, String.valueOf(defaultValue)));
    }

    /**
     * Get value from a property. Use AsType methods as required.
     * You can specify a default as parameter or in the ConfigManager class.
     * @param key e.g. "ConfigManager.details.version"
     * @return value
     */
    public double getPropAsDouble(String key){
        return Float.parseFloat(this.getProp(key));
    }

    /**
     * Get value from a property. Use AsType methods as required.
     * You can specify a default as parameter or in the ConfigManager class.
     * @param key e.g. "ConfigManager.details.version"
     * @param defaultValue e.g. 1.0
     * @return value
     */
    public double getPropAsDouble(String key, double defaultValue){
        return Double.parseDouble(this.getProp(key, String.valueOf(defaultValue)));
    }

    /**
     * Get value from a property. Use AsType methods as required.
     * You can specify a default as parameter or in the ConfigManager class.
     * @param key e.g. "ConfigManager.details.booleansWork"
     * @return value
     */
    public boolean getPropAsBoolean(String key){
        return Boolean.parseBoolean(this.getProp(key));
    }

    /**
     * Get value from a property. Use AsType methods as required.
     * You can specify a default as parameter or in the ConfigManager class.
     * @param key e.g. "ConfigManager.details.booleansWork"
     * @param defaultValue e.g. true
     * @return value
     */
    public boolean getPropAsBoolean(String key, boolean defaultValue){
        return Boolean.parseBoolean(this.getProp(key, String.valueOf(defaultValue)));
    }
}