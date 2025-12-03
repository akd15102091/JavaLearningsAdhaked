package UberLLDs.EventAssignmentModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

public class EventAssignmentManager {
    // Rooms sorted by capacity for best-fit selection
    private final TreeMap<Integer, List<Room>> roomsByCapacity = new TreeMap<>();

    // Register a room
    public void addRoom(Room room) {
        roomsByCapacity.computeIfAbsent(room.capacity, k -> new ArrayList<>())
                       .add(room);
    }

    // Best fit: smallest room whose capacity >= required
    private List<Room> getCandidateRooms(Event e) {
        NavigableMap<Integer, List<Room>> sub = roomsByCapacity.tailMap(e.requiredCapacity, true);
        List<Room> list = new ArrayList<>();
        for (var entry : sub.entrySet()) {
            list.addAll(entry.getValue());
        }
        return list;
    }

    // Main API
    public String scheduleEvent(Event e) {
        for (Room r : getCandidateRooms(e)) {
            if (r.tryBook(e)) {
                return r.roomId;
            }
        }
        return null; // no room free
    }

    public List<Interval> getRoomSchedule(String roomId) {
        for (var entry : roomsByCapacity.values()) {
            for (Room r : entry) {
                if (r.roomId.equals(roomId))
                    return r.getSchedule();
            }
        }
        return Collections.emptyList();
    }
}
