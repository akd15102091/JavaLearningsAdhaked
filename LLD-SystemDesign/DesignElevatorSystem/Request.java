package DesignElevatorSystem;


public class Request implements Comparable<Request> {
    int floor;
    ElevatorState direction;

    public Request(int floor, ElevatorState direction) {
        this.floor = floor;
        this.direction = direction;
    }

    @Override
    public int compareTo(Request other) {
        return Integer.compare(this.floor, other.floor);
    }
}
