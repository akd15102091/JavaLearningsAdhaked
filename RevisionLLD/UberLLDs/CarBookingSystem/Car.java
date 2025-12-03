package UberLLDs.CarBookingSystem;

import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantLock;

public class Car {
    private final int id;
    private final ReentrantLock lock = new ReentrantLock();
    // Bookings sorted by start time
    private final TreeSet<Interval> bookings = new TreeSet<>();

    public Car(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    // -------- Internal helper: must be called WITH lock held --------
    private boolean isAvailableUnsafe(Interval req) {
        // Find the interval with the greatest start <= req.start
        Interval floor = bookings.floor(req);
        if (floor != null && floor.overlaps(req)) {
            return false;
        }

        // Find the interval with the smallest start >= req.start
        Interval ceil = bookings.ceiling(req);
        if (ceil != null && ceil.overlaps(req)) {
            return false;
        }

        return true;
    }

    // Public availability check: safe and O(log K)
    public boolean isAvailable(Interval req) {
        lock.lock();
        try {
            return isAvailableUnsafe(req);
        } finally {
            lock.unlock();
        }
    }

    // Try to reserve this car for the given interval.
    // O(log K) and thread-safe.
    public boolean reserve(Interval req) {
        lock.lock();
        try {
            if (!isAvailableUnsafe(req)) {
                return false;
            }
            bookings.add(req);
            return true;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "Car{" + "id=" + id + ", bookings=" + bookings + '}';
    }
}
