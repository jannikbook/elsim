package main.java.elsim.models;

import java.util.List;

public class ElevatorShaft {

    private Car ElevatorCar;
    private List<Floor> Floors;

    public ElevatorShaft(int maxPersonNumber, int maxMass, Rectangle maxCarArea, int elevatorSpeed, int numberOfFloors) {
        ElevatorCar = new Car(maxPersonNumber, maxMass, maxCarArea, elevatorSpeed, numberOfFloors);
    }

    public Car getElevatorCar() {
        return ElevatorCar;
    }

    public List<Floor> getFloors() {
        return Floors;
    }
    public void addFloor(int height) {
        Floors.add(new Floor(height));
    }

    public double distanceToFloor(int floor) {
        double distance = 0;
        int from = 0;
        int to = 0;
        if (floor>0) {
            from = floor;
            to = 0;
        } else {
            from = 0;
            to = floor;
        }
        for (int i = from; i<to; i++) {
            distance+=this.getFloors().get(i).getHeight();
        }
        return distance;
    }

}
