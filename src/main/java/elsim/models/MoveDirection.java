package main.java.elsim.models;

/**
 * MoveDirection is an enum for the current servicing direction of the elevator.
 * {@Code Up} and {@Code Down} are used until there are no more floors to be serviced in the respective direction.
 * When the elevator is awaiting new hall calls the state will be {@Code Hold}.
 */
public enum MoveDirection {
    Up,
    Down,
    Hold
}
