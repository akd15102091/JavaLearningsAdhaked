package DesignElevatorSystem;

public class DriverMain {
    public static void main(String[] args) {
        ElevatorSystem system = ElevatorSystem.getInstance(3, 5);
        system.requestElevator(3, ElevatorState.MOVING_UP);
        system.requestElevator(7, ElevatorState.MOVING_DOWN);
        system.requestElevator(1, ElevatorState.MOVING_UP);
    }
}
