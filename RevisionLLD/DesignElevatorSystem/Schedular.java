package DesignElevatorSystem;

import java.util.List;

public interface Schedular {
    public Elevator pickElevator(Request request, List<Elevator> elevators);
}
