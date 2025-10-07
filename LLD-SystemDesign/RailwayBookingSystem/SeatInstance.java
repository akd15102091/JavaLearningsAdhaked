package RailwayBookingSystem;

import java.util.ArrayList;
import java.util.List;

public class SeatInstance {
    int seatNumber;
    List<Booking> bookings = new ArrayList<>();

    public SeatInstance(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public boolean isAvailable(int fromIndex, int toIndex) {
        for (Booking b : bookings) {
            if (!(toIndex <= b.fromIndex || fromIndex >= b.toIndex)) {
                return false;
            }
        }
        return true;
    }

    public void addBooking(String passengerName, int fromIndex, int toIndex) {
        bookings.add(new Booking(passengerName, fromIndex, toIndex));
    }

    public List<Booking> getBookings() {
        return bookings;
    }
}
