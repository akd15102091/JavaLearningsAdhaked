package UberLLDs.EventAssignmentModule;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantLock;

public class Room {
    final String roomId;
    final int capacity;

    // store schedule in sorted order
    final TreeSet<Interval> schedule = new TreeSet<>();

    // per-room lock (important!)
    final ReentrantLock lock = new ReentrantLock();

    public Room(String roomId, int capacity) {
        this.roomId = roomId;
        this.capacity = capacity;
    }

    boolean tryBook(Event e) {
        Interval newIv = new Interval(e.start, e.end);

        lock.lock();
        try {
            Interval floor = schedule.floor(newIv);
            Interval ceil = schedule.ceiling(newIv);

            if (floor != null && floor.overlaps(newIv)) return false;
            if (ceil != null && ceil.overlaps(newIv)) return false;

            schedule.add(newIv);
            return true;
        } finally {
            lock.unlock();
        }
    }

    List<Interval> getSchedule() {
        lock.lock();
        try {
            return new ArrayList<>(schedule);
        } finally {
            lock.unlock();
        }
    }
}
