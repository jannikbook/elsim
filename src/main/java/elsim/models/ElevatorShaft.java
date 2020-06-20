package main.java.elsim.models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ElevatorShaft {

    private Car elevatorCar;
    private List<Floor> floors;
    private Floor carFloor;
    private MoveDirection carDir;
    private double carSpeed = 21.00;

    public MoveDirection getDir() {
        return carDir;
    }

    public ElevatorShaft(Car c) {
        elevatorCar = c;
        floors = new ArrayList<Floor>();
        carFloor = null;
        carDir = MoveDirection.Up;
    }

    public Car getElevatorCar() {
        return elevatorCar;
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public void addFloor(Floor f) {
        floors.add(f);
        if (carFloor == null) {             //Kabine startet im untersten Geschoss
            carFloor = f;
        }
    }

    public double distanceToFloor(Floor floor) {
        double distance = 0;
        int save;                           //Warte auf implementierung von Car.getCurrentFloor();
        int from = floors.indexOf(carFloor);
        int to = floors.indexOf(floor);
        if (from>to) {
            save = to;
            to = from;
            from = save;
        }
        for (int i = from; i<to; i++) {
            distance+=this.getFloors().get(i).getHeight();
        }
        return distance;
    }

    public Floor nextFloor() {
        if (carDir == MoveDirection.Up) {
            for (int i = floors.indexOf(carFloor); i<floors.size();i++) {
                if (floors.get(i).getButtonPressedUp()) return floors.get(i);
            }
        } else if (carDir == MoveDirection.Down) {
            for (int i = floors.indexOf(carFloor); i>=0;i--) {
                if (floors.get(i).getButtonPressedDown()) return floors.get(i);
            }
        }
        return null;
    }

    public int getSecondsToFloor(double distance) {
        return (int) (distance/carSpeed);
    }

    public void moveCar() {
        getSecondsToFloor(distanceToFloor(nextFloor()));
    }



}
