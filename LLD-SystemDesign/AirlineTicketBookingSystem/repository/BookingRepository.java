package AirlineTicketBookingSystem.repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import AirlineTicketBookingSystem.Booking;

public class BookingRepository {
    Map<String, Booking> map = new ConcurrentHashMap<>();

    public void save(Booking b) { map.put(b.pnr, b); }

    public Optional<Booking> get(String pnr) {
        return Optional.ofNullable(map.get(pnr));
    }
}
