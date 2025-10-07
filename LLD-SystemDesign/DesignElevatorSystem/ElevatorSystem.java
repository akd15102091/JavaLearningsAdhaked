package DesignElevatorSystem;

import java.util.ArrayList;
import java.util.List;

// Singleton
public class ElevatorSystem {
    private static ElevatorSystem instance;
    private List<Elevator> elevators;

    private ElevatorSystem(int numberOfElevators, int capacity) {
        elevators = new ArrayList<>();
        for (int i = 0; i < numberOfElevators; i++) {
            Elevator elevator = new Elevator(i, capacity);
            elevators.add(elevator);
            new Thread(elevator).start();
        }
    }

    public static synchronized ElevatorSystem getInstance(int numberOfElevators, int capacity) {
        if (instance == null) {
            instance = new ElevatorSystem(numberOfElevators, capacity);
        }
        return instance;
    }

    public void requestElevator(int floor, ElevatorState direction) {
        Elevator bestElevator = findBestElevator(floor, direction);
        if (bestElevator != null) {
            bestElevator.addRequest(new Request(floor, direction));
        }
    }

    private Elevator findBestElevator(int floor, ElevatorState direction) {
        Elevator bestElevator = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator elevator : elevators) {
            // Prefer an idle elevator or one already moving in the requested direction
            if (elevator.getState() == ElevatorState.IDLE || 
                (elevator.getState() == direction && ((direction == ElevatorState.MOVING_UP && elevator.getCurrentFloor() <= floor) ||
                                                (direction == ElevatorState.MOVING_DOWN && elevator.getCurrentFloor() >= floor)))) {
                int distance = Math.abs(elevator.getCurrentFloor() - floor);
                if (distance < minDistance) {
                    bestElevator = elevator;
                    minDistance = distance;
                }
            }
        }

        return bestElevator != null ? bestElevator : elevators.get(0); // Fallback if all are busy

    }
}
