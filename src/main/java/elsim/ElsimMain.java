package main.java.elsim;

import main.java.elsim.config.ConfigManager;
import main.java.elsim.models.Car;
import main.java.elsim.models.ElevatorShaft;
import main.java.elsim.simulation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ElsimMain {

	public static void main(String[] args)
			throws SimulationAlreadyInitializedException, SimulationAlreadyRunningException, SimulationNotInitializedException {

		var configManager = ConfigManager.getInstance();

		if (args.length > 0) {
			configManager.readConfig(args[0]);
		} else {
			configManager.readConfig(); // This reads from file or creates a default config
		}

		var timeStart = LocalDateTime.now();

		var car = new Car(); // construct from config
		var elevatorShaft = new ElevatorShaft(car);
		var eventManager = new SimEventManager();

		Simulation.initialize(elevatorShaft, eventManager, timeStart, timeStart.plus(1, ChronoUnit.DAYS));
		Simulation.run();
	}

}
