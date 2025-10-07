package RailwayBookingSystem;

public class Booking {
    String passengerName;
    int fromIndex;
    int toIndex;

    public Booking(String passengerName, int fromIndex, int toIndex) {
        this.passengerName = passengerName;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
    }
}
