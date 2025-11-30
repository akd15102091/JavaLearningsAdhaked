package AirlineTicketBookingSystem;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class FlightInstance {
    public long id;
    public Flight flight;
    public LocalDate date;

    // seatNumber -> status
    public Map<String, SeatStatus> seats = new HashMap<>();

    public ReentrantLock lock = new ReentrantLock();

    public FlightInstance(long id, Flight flight, LocalDate date) {
        this.id = id; this.flight = flight; this.date = date;
        for (int i = 1; i <= flight.totalSeats; i++)
            seats.put("S" + i, SeatStatus.AVAILABLE);
    }
}
