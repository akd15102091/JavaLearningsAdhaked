package DesignTrainBookingSystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DemoMain {
    public static void main(String[] args) {
        TrainRepository trainRepo = new TrainRepository();
        TrainInstanceRepository instanceRepo = new TrainInstanceRepository();
        BookingService bookingService = new BookingService(trainRepo, instanceRepo);

        // Build a sample train with 10 stations and 2 seats to clearly show overlapping segments
        List<String> stations = new ArrayList<>();
        for (int i = 1; i <= 10; i++) stations.add("S" + i);
        Train t1 = new Train("T1", "Express-1", stations, 2);
        trainRepo.addTrain(t1);


        LocalDate date = LocalDate.of(2025, 11, 12);


        // Pre-fill seats: seat allocations that fill station 1->3 and 6->10
        // We'll book so that all seats are filled for those segments, but segment 4->5 remains free.


        // Bookings to fill segments 1->3
        Booking b1 = bookingService.book("T1", date, "Alice", "S1", "S3");
        Booking b2 = bookingService.book("T1", date, "Bob", "S1", "S3");
        System.out.println("Prebooked: " + b1);
        System.out.println("Prebooked: " + b2);


        // Booking to fill segments 6->10 (S6->S10)
        Booking b3 = bookingService.book("T1", date, "Carol", "S6", "S10");
        Booking b4 = bookingService.book("T1", date, "Dave", "S6", "S10");
        System.out.println("Prebooked: " + b3);
        System.out.println("Prebooked: " + b4);


        // Now attempt to search & book for passenger wanting to go S4->S5 (segment 4)
        System.out.println("\nSearching trains for S4->S5 on " + date + "...");
        List<TrainInstance> found = bookingService.search("S4", "S5", date);
        System.out.println("Trains found: " + found);


        System.out.println("Attempting booking for Eve S4->S5...");
        Booking b5 = bookingService.book("T1", date, "Eve", "S4", "S5");
        System.out.println("Booking result: " + b5);


        // Print bookings per train per date
        System.out.println("\nAll bookings for " + date + ":");
        bookingService.printAllBookingsForDate(date);


        // Try booking overlapping segment where no seats should be available (e.g., S2->S7 overlaps filled segments)
        System.out.println("\nAttempt booking Frank S2->S7 (overlaps filled segments) ...");
        Booking b6 = bookingService.book("T1", date, "Frank", "S2", "S7");
        System.out.println("Booking result: " + b6 + " (null means no seat available)");


        // Example search showing no availability for S2->S7
        List<TrainInstance> found2 = bookingService.search("S2", "S7", date);
        System.out.println("Search S2->S7 found: " + found2);


        // The demo above uses only 2 seats; with more seats concurrency/throughput increases.
    }
}
