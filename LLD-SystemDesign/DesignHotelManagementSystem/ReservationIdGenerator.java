package DesignHotelManagementSystem;

// Singleton Pattern for Reservation ID generation
public class ReservationIdGenerator {
    private static ReservationIdGenerator instance;
    private int idCounter = 0;

    private ReservationIdGenerator() {}

    public static synchronized ReservationIdGenerator getInstance() {
        if (instance == null) {
            instance = new ReservationIdGenerator();
        }
        return instance;
    }

    public synchronized int generateId() {
        return ++idCounter;
    }
}
