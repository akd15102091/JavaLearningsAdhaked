package SpillageMinimizingRoomScheduler;

import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Room {
    String roomId;
    int capacity;
    TreeSet<Interval> bookings;
    final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    Room(String id, int cap) {
        this.roomId = id;
        this.capacity = cap;
        this.bookings = new TreeSet<>();
    }
}
