package main.java.elsim;

import main.java.elsim.config.ConfigManager;
import main.java.elsim.models.Car;
import main.java.elsim.models.ElevatorShaft;
import main.java.elsim.simulation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ElsimMain {

	private static final Logger LOGGER = Logger.getLogger(ElsimMain.class.getPackageName());

	public static void main(String[] args) throws
			SimulationAlreadyInitializedException,
			SimulationAlreadyRunningException,
			SimulationNotInitializedException,
			IOException {

		LOGGER.setUseParentHandlers(false);
		LOGGER.setLevel(Level.FINER);

		var fileHandler = new FileHandler("log.txt");
		var formatter = new LogFormatter();
		fileHandler.setFormatter(formatter);
		LOGGER.addHandler(fileHandler);

		var configManager = ConfigManager.getInstance();

		if (args.length > 0) {
			configManager.readConfig(args[0]);
		} else {
			configManager.readConfig(); // This reads from file or creates a default config
		}

		var timeStart = LocalDateTime.now();

		var car = new Car(); // construct from config
		var elevatorShaft = new ElevatorShaft(car);
		var eventManager = new SimEventManager(timeStart);

		Simulation.initialize(elevatorShaft, eventManager, timeStart, timeStart.plus(1, ChronoUnit.DAYS));
		Simulation.run();
	}

}
