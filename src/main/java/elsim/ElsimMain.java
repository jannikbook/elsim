package main.java.elsim;

import main.java.elsim.models.Car;
import main.java.elsim.models.ElevatorShaft;
import main.java.elsim.models.Floor;
import main.java.elsim.simulation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;

public class ElsimMain {

	public static void main(String[] args) throws SimulationAlreadyInitializedException, SimulationAlreadyRunningException, SimulationNotInitializedException {
		var timeStart = LocalDateTime.now();

		var car = new Car(10, 100, 5.0 * 5, 2);
		var floors = new ArrayList<>(Arrays.asList(
				new Floor(-1, 300),
				new Floor(0, 350),
				new Floor(1, 300),
				new Floor(2, 300),
				new Floor(3, 400),
				new Floor(4, 400),
				new Floor(5, 382),
				new Floor(6, 260)
		));

		var elevatorShaft = new ElevatorShaft(car, floors);
		var eventManager = new SimEventManager();

		Simulation.initialize(elevatorShaft, eventManager, timeStart, timeStart.plus(1, ChronoUnit.DAYS));
		Simulation.run();
	}

}
