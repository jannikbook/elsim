package main.java.elsim.models;

import java.time.Duration;
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

    public ElevatorShaft(Car c, List<Floor> floors) {
        this.elevatorCar = c;
        this.elevatorCar.setElevatorShaft(this);

        this.floors = floors;
        this.carFloor = floors.get(0);
        this.carDir = MoveDirection.Up;
    }

    public Car getElevatorCar() {
        return elevatorCar;
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public void addFloor(Floor f) {
        floors.add(f);
    }

    private double distanceToFloor(Floor floor) {
        double distance = 0;
        int save;                           //Warte auf implementierung von Car.getCurrentFloor();
        int from = floors.indexOf(carFloor);
        int to = floors.indexOf(floor);
        if (from > to) {
            save = to;
            to = from;
            from = save;
        }
        for (int i = from; i < to; i++) {
            distance += this.getFloors().get(i).getHeight();
        }
        return distance;
    }

    public Floor nextFloor() {
        if (carDir == MoveDirection.Up) {
            for (int i = floors.indexOf(carFloor); i < floors.size(); i++) {
                if (floors.get(i).getButtonPressedUp()) {
                    return floors.get(i);
                }
            }
        } else if (carDir == MoveDirection.Down) {
            for (int i = floors.indexOf(carFloor); i >= 0; i--) {
                if (floors.get(i).getButtonPressedDown()) {
                    return floors.get(i);
                }
            }
        }
        return null;
    }

    private Duration getDurationForDistance(double distance) {
        return Duration.ofMillis(1000 * (int)(distance / carSpeed));
    }

    public Duration moveCar() {
        var nextFloor = nextFloor();
        if (nextFloor == null) {
            return Duration.ZERO;
        }

        var distance = distanceToFloor(nextFloor);
        return getDurationForDistance(distance);
    }

    public Floor getCurrentCarFloor() {
        return this.carFloor;
    }

    public void arriveAtFloor(Floor floor) {
        if (!this.floors.contains(floor)) {
            throw new IllegalArgumentException("floor");
        }

        this.carFloor = floor;
    }
}
