package DesignTrainBookingSystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

// ---------- TrainInstance: represents a train on a particular date ----------
public class TrainInstance {
    final Train train;
    final LocalDate date;
    final int seats; // total seats
    final int segments; // train.stations.size() - 1


    // occupancy[seat][segment] == true means that seat is occupied on that segment
    private final boolean[][] occupancy;
    private final ReentrantLock lock = new ReentrantLock();

    // maintain bookings for printing/reporting
    private final List<Booking> bookings = new ArrayList<>();

    public TrainInstance(Train train, LocalDate date) {
        this.train = train;
        this.date = date;
        this.seats = train.totalSeats;
        this.segments = train.stationsCount() - 1;
        this.occupancy = new boolean[seats][segments];
    }


    /**
    * Attempt to find and allocate a seat for requested segment [fromIndex, toIndex)
    * Returns Booking on success, or null if no seat available.
    * This method is thread-safe.
    */
    public Booking tryBook(String passengerName, String fromStation, String toStation, int fromIndex, int toIndex) {
        // validate indices
        if (!(fromIndex >= 0 && toIndex > fromIndex && toIndex <= train.stationsCount())) {
            throw new IllegalArgumentException("Invalid station indices");
        }


        lock.lock();
        try {
            // search for a seat which is free on all required segments
            for (int s = 0; s < seats; s++) {
                if (isSeatFreeOnSegments(s, fromIndex, toIndex)) {
                    // allocate
                    for (int seg = fromIndex; seg < toIndex; seg++) occupancy[s][seg] = true;
                    String bookingId = IdGenerator.nextId("BKG");
                    Booking b = new Booking(bookingId, train.trainId, date, passengerName, fromStation, toStation, s, fromIndex, toIndex);
                    bookings.add(b);
                    return b;
                }
            }
            return null;
        } finally {
            lock.unlock();
        }
    }


    private boolean isSeatFreeOnSegments(int seatNo, int fromIndex, int toIndex) {
        for (int seg = fromIndex; seg < toIndex; seg++) {
            if (occupancy[seatNo][seg]) return false;
        }
        return true;
    }


    /**
    * Quick check whether there's at least one seat free for requested segment
    * Non-blocking check: obtains lock briefly to read occupancy safely
    */
    public boolean hasAvailability(int fromIndex, int toIndex) {
        lock.lock();
        try {
            for (int s = 0; s < seats; s++) {
                if (isSeatFreeOnSegments(s, fromIndex, toIndex)) return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public List<Booking> getBookings() {
    lock.lock();
        try {
            return new ArrayList<>(bookings);
        } finally { lock.unlock(); }
    }

    public String toString() { return train.toString() + "@" + date; }
}
