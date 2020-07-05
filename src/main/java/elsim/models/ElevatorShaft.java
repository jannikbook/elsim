package main.java.elsim.models;

import main.java.elsim.config.ConfigManager;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

/**
 * ElevatorShaft
 * Handles a car and many floors.
 * @author lBlankemeyer
 */

public class ElevatorShaft {

    private Car elevatorCar;
    private List<Floor> floors;
    private Floor carFloor;
    private MoveDirection carDir;
    private double carSpeed;

    public MoveDirection getDir() {
        return carDir;
    }

    public ElevatorShaft(Car c, List<Floor> floors) {
        this.elevatorCar = c;
        this.elevatorCar.setElevatorShaft(this);

        this.floors = floors;
        this.carFloor = floors.get(0);
        this.carDir = MoveDirection.Up;

        carSpeed=ConfigManager.getInstance().getPropAsInt("carSpeed");
    }

    public ElevatorShaft(Car c) {
        this.elevatorCar = c;
        this.elevatorCar.setElevatorShaft(this);

        this.loadFloors();
        this.carFloor = floors.get(0);
        this.carDir = MoveDirection.Up;
    }

    private void loadFloors(){
        this.floors = new LinkedList<Floor>();
        for (int i = 0; i < ConfigManager.getInstance().getPropAsInt("ElevatorShaft.floors.length"); i++){
            String[] vars = ConfigManager.getInstance().getProp("ElevatorShaft.floors." + i).split(";");
            this.floors.add(new Floor(Integer.parseInt(vars[0]), Integer.parseInt(vars[1])));
        }
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

    //Calculation of Distance to next Floor
    private int distanceToFloor(Floor floor) {
        int distance = 0;
        int save;
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

    //Get nextFloor (next floor between next floor where someone leaves and next floor where someone enters)
    public Floor nextFloor() {
        Floor dest = nextFloorEnters();
        Floor arr = nextFloorLeaves();
        if (dest != null && arr != null) {
            if (carDir == MoveDirection.Up) {
                if (floors.indexOf(dest) < floors.indexOf(arr)) {
                    return dest;
                } else {
                    return arr;
                }
            } else if (carDir == MoveDirection.Down) {
                if (floors.indexOf(dest) > floors.indexOf(arr)) {
                    return dest;
                } else {
                    return arr;
                }
            }
        }
        if (dest != null && arr == null) return dest;
        if (dest == null && arr != null) return arr;
        return null;
    }

    //Next floor where a Passenger enters
    public Floor nextFloorEnters() {
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

    //Next floor where a Passenger leaves
    public Floor nextFloorLeaves() {
        Floor f = null;
        if (carDir == MoveDirection.Up) {
            for (Passenger p : elevatorCar.getCurrentPassengers()) {
                if (floors.indexOf(p.getFloorDestination())>floors.indexOf(carFloor)) {
                    if (floors.indexOf(f)>floors.indexOf(p.getFloorDestination())||f==null) {
                        f=p.getFloorDestination();
                    }
                }
            }
        } else if (carDir == MoveDirection.Down) {
            for (Passenger p : elevatorCar.getCurrentPassengers()) {
                if (floors.indexOf(p.getFloorDestination())<floors.indexOf(carFloor)) {
                    if (floors.indexOf(f)<floors.indexOf(p.getFloorDestination())||f==null) {
                        f=p.getFloorDestination();
                    }
                }
            }
        }
        return null;
    }

    private Duration getDurationForDistance(int distance) {
        return Duration.ofMillis((long) (1000 * (distance/100 / carSpeed)));
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
