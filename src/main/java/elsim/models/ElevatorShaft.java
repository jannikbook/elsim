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
        int save;               //Warte auf implementierung von Car.getCurrentFloor();
        int from = 0;
        int to = floor;

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

}
