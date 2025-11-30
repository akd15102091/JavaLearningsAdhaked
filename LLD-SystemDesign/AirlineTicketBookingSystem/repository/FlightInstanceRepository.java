package AirlineTicketBookingSystem.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import AirlineTicketBookingSystem.FlightInstance;

public class FlightInstanceRepository {
    Map<Long, FlightInstance> store = new ConcurrentHashMap<>();

    // Efficient index: "BLR-DEL-2025-12-20" -> [fi1,fi2]
    Map<String, List<Long>> index = new ConcurrentHashMap<>();

    public void save(FlightInstance fi) {
        store.put(fi.id, fi);
        String key = key(fi.flight.origin, fi.flight.destination, fi.date);
        index.computeIfAbsent(key, k -> new ArrayList<>()).add(fi.id);
    }

    public List<FlightInstance> search(String origin, String dest, LocalDate date) {
        String key = key(origin, dest, date);
        List<Long> ids = index.getOrDefault(key, Collections.emptyList());
        List<FlightInstance> res = new ArrayList<>();
        for (Long id : ids) res.add(store.get(id));
        return res;
    }

    public FlightInstance get(long id) {
        return store.get(id);
    }

    private String key(String o, String d, LocalDate dt) {
        return o + "|" + d + "|" + dt.toString();
    }
}
