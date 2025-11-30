package AirlineTicketBookingSystem.repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import AirlineTicketBookingSystem.SeatHold;

public class SeatHoldRepository {
    Map<String, SeatHold> map = new ConcurrentHashMap<>();

    public void save(SeatHold h) { map.put(h.holdId, h); }

    public Optional<SeatHold> get(String id) {
        SeatHold h = map.get(id);
        if (h == null) return Optional.empty();
        if (h.isExpired()) { map.remove(id); return Optional.empty(); }
        return Optional.of(h);
    }

    public void delete(String id) { map.remove(id); }
}
