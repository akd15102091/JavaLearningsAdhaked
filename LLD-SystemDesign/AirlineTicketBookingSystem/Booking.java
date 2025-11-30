package AirlineTicketBookingSystem;

import java.time.LocalDateTime;
import java.util.List;

public class Booking {
    public String pnr;
    long flightInstanceId;
    long userId;
    List<Passenger> passengers;
    LocalDateTime createdAt = LocalDateTime.now();

    public Booking(String pnr, long fi, long user, List<Passenger> list) {
        this.pnr = pnr; this.flightInstanceId = fi;
        this.userId = user; this.passengers = list;
    }
}
