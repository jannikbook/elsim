package main.java.elsim.models;

import main.java.elsim.config.ConfigManager;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Contains all Information of the elevator shaft. The elevator shaft also contains the elevator car.
 * The elevator car is controlled from the elevator shaft to move it to the individual floors.
 * In addition, this class controls the algorithm with which the elevator car travels to each floor.
 * @author lBlankemeyer
 */
public class ElevatorShaft {

    private static final Logger LOGGER = Logger.getLogger(ElevatorShaft.class.getName());
    private Car elevatorCar;
    private List<Floor> floors;
    private Floor carFloor;
    private MoveDirection carDir;
    private double carSpeed;

    /**
     * Public GETTER of move direction.
     * @return Direction of ar movement
     */
    public MoveDirection getDir() {
        return carDir;
    }

    /**
     * Public constructor, which creates an elevator shaft with an elevator car.
     * Elevator car speed is set by the config.
     * @param c The elevator car, which is contained by the elevator Shaft
     */
    public ElevatorShaft(Car c) {
        this.elevatorCar = c;
        this.elevatorCar.setElevatorShaft(this);
        this.loadFloors();
        this.carFloor = floors.get(0);
        this.carDir = MoveDirection.Up;

        carSpeed = ConfigManager.getInstance().getPropAsInt("ElevatorShaft.carSpeed");
    }

    /**
     * First reads the floor count from the config.
     * Then each floor is read from the config file and created inside the elevator shaft.
     */
    private void loadFloors(){
        this.floors = new LinkedList<Floor>();
        for (int i = 0; i < ConfigManager.getInstance().getPropAsInt("ElevatorShaft.floors.length"); i++){
            String[] vars = ConfigManager.getInstance().getProp("ElevatorShaft.floors." + i).split(";");
            var temp = vars[2].split("\\.\\.");
            this.floors.add(new Floor(Integer.parseInt(vars[0]), Integer.parseInt(vars[1]), Integer.parseInt(temp[0]), Integer.parseInt(temp[1])));
        }
    }

    /**
     * Public GETTER for elevator car.
     * @return elevator car, which is contained by the elevator shaft.
     */
    public Car getElevatorCar() {
        return elevatorCar;
    }

    /**
     * Public GETTER for elevator car.
     * @return List of floors, which is contained by the elevator shaft.
     */
    public List<Floor> getFloors() {
        return floors;
    }

    /**
     * Adds up all floor heights of floors between start and destination floor.
     * @param floor Floor to which the distance is to be measured.
     * @return Distance in centimeters.
     */
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

    /**
     * Method that finds out which floor of the two auxiliary algorithms has determined the optimal floor.
     * @return Floor, if there is an optimal Floor. Null, if there is no optimal Floor.
     */
    private Floor nextFloor() {
        if (carDir == MoveDirection.Hold) carDir = MoveDirection.Up;
        Floor enter = nextFloorEnters();
        Floor leaver = nextFloorLeaves();
        if (enter != null && leaver != null) {
            if (carDir == MoveDirection.Up) {
                if (floors.indexOf(enter) < floors.indexOf(leaver)) {
                    return enter;
                } else {
                    return leaver;
                }
            } else if (carDir == MoveDirection.Down) {
                if (floors.indexOf(enter) > floors.indexOf(leaver)) {
                    return enter;
                } else {
                    return leaver;
                }
            }
        }
        if (enter != null && leaver == null) return enter;
        if (enter == null && leaver != null) return leaver;
        return null;
    }

    /**
     * Part of the algorithm, to find the next destination floor.
     * The elevator shaft pays attention to the nearest floor and queries the button statuses.
     * If the elevator shaft finds no floor, it
     * @return Next destination floor in terms of passengers waiting for the elevator car
     */
    private Floor nextFloorEnters() {
        if (carDir == MoveDirection.Up) {
            for (int i = floors.indexOf(carFloor) + 1; i < floors.size(); i++) {
                if (floors.get(i).getButtonPressedUp()) {
                    return floors.get(i);
                }
            }
        } else if (carDir == MoveDirection.Down) {
            for (int i = floors.indexOf(carFloor) - 1; i >= 0; i--) {
                if (floors.get(i).getButtonPressedDown()) {
                    return floors.get(i);
                }
            }
        }
        for (int i = floors.indexOf(carFloor) + 1; i < floors.size(); i++) {
            if (floors.get(i).getButtonPressedDown()) {
                return floors.get(i);
            }
        }
        for (int i = floors.indexOf(carFloor) - 1; i >= 0; i--) {
            if (floors.get(i).getButtonPressedUp()) {
                return floors.get(i);
            }
        }
        return null;

    }

    /**
     * Part of the algorithm, to find the next destination floor.
     * The elevator shaft pays attention to the nearest floor, which is also a passenger's destination floor.
     * @return Next destination floor in terms of passengers in the elevator car
     */
    private Floor nextFloorLeaves() {
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
        return f;
    }

    /**
     * Calculates the duration, the car needs, to move from floor to floor.
     * @param distance Distance, which needs to be traveled.
     * @return Return of Duration
     */
    private Duration getDurationForDistance(int distance) {
        return Duration.ofMillis((long) (1000 * (distance/100 / carSpeed)));
    }

    /**
     * Public method which gets called by events to perform the car movement.
     * @return Duration of moving car
     */
    public Duration moveCar() {
        var nextFloor = nextFloor();

        if (nextFloor == null) {
            if (carDir == MoveDirection.Up) {
                carDir = MoveDirection.Down;
                nextFloor = nextFloor();
            } else if (carDir == MoveDirection.Down) {
                carDir = MoveDirection.Up;
                nextFloor = nextFloor();
            }
        }

        if (nextFloor == null) {
            LOGGER.fine("No floor to move to. Changing to hold state.");
            carDir = MoveDirection.Hold;
            return Duration.ZERO;
        }

        var distance = distanceToFloor(nextFloor);
        this.moveToFloor(nextFloor);
        if (this.getElevatorCar().getCurrentPassengers().size() == 0) {
            carDir = MoveDirection.Hold;
        }

        return getDurationForDistance(distance);
    }

    /**
     * Getter of current floor of elevator car.
     * @return Floor of elevator car.
     */
    public Floor getCurrentCarFloor() {
        return this.carFloor;
    }

    /**
     * This Method transfers the elevator car to a specific floor.
     * @param floor Floor, where the elevator car should be moved to.
     */
    private void moveToFloor(Floor floor) {
        if (!this.floors.contains(floor)) {
            throw new IllegalArgumentException("floor");
        }

        this.carFloor = floor;
    }
}
