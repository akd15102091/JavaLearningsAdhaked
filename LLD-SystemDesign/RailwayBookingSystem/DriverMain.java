package RailwayBookingSystem;

import java.util.Arrays;
import java.util.List;

public class DriverMain {
    public static void main(String[] args) {
        BookingService bookingService = new BookingService();

        // Setup trains
        bookingService.addTrain("Express101", Arrays.asList("A", "B", "C", "D"), 2);
        bookingService.addTrain("Express102", Arrays.asList("X", "Y", "Z"), 1);

        // Add schedule (dates)
        bookingService.addTrainDate("Express101", "2025-05-18");
        bookingService.addTrainDate("Express101", "2025-05-19");
        bookingService.addTrainDate("Express102", "2025-05-18");

        // Bookings
        bookingService.book("Express101", "2025-05-18", "Alice", "A", "B");
        bookingService.book("Express101", "2025-05-18", "Bob", "B", "C");
        bookingService.book("Express101", "2025-05-18", "Charlie", "A", "C");

        bookingService.book("Express101", "2025-05-19", "David", "A", "C");
        bookingService.book("Express102", "2025-05-18", "Eve", "X", "Z");

        // Search
        System.out.println("\n🔍 Search Results:");
        List<TrainInstance> searchResults = bookingService.search("A", "C", "2025-05-18");
        for (TrainInstance ti : searchResults) {
            System.out.println("Train: " + ti.train.name + " Date: " + ti.date);
        }

        // Show bookings
        bookingService.showBookings("Express101", "2025-05-18");
        bookingService.showBookings("Express101", "2025-05-19");
        bookingService.showBookings("Express102", "2025-05-18");
    }
}
