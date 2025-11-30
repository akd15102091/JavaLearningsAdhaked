package AirlineTicketBookingSystem;

import java.time.LocalDate;
import java.util.List;

import AirlineTicketBookingSystem.repository.BookingRepository;
import AirlineTicketBookingSystem.repository.FlightInstanceRepository;
import AirlineTicketBookingSystem.repository.SeatHoldRepository;
import AirlineTicketBookingSystem.services.BookingService;
import AirlineTicketBookingSystem.services.InventoryService;
import AirlineTicketBookingSystem.services.SearchService;

public class Main {
    public static void main(String[] args) {
        // ---------------- Setup Repositories ----------------
        FlightInstanceRepository fiRepo = new FlightInstanceRepository();
        SeatHoldRepository holdRepo = new SeatHoldRepository();
        BookingRepository bookingRepo = new BookingRepository();

        // ---------------- Setup Services ----------------
        SearchService searchService = new SearchService(fiRepo);
        InventoryService inventoryService = new InventoryService(fiRepo, holdRepo);
        BookingService bookingService = new BookingService(fiRepo, holdRepo, bookingRepo);

        // ---------------- Create Flights ----------------
        Flight f1 = new Flight(1, "AI-202", "BLR", "DEL", 6);
        Flight f2 = new Flight(2, "AI-204", "BLR", "DEL", 6);

        // ---------------- Create Flight Instances (for a date) ----------------
        FlightInstance fi1 = new FlightInstance(101, f1, LocalDate.of(2025, 1, 12));
        FlightInstance fi2 = new FlightInstance(102, f2, LocalDate.of(2025, 1, 12));

        fiRepo.save(fi1);
        fiRepo.save(fi2);

        // ---------------- Search ----------------
        System.out.println("Searching BLR → DEL flights for 2025-01-12 ...");
        List<FlightInstance> results =
                searchService.search("BLR", "DEL", LocalDate.of(2025, 1, 12));

        for (FlightInstance fi : results) {
            System.out.println("  Found FlightInstance: " +
                    fi.flight.flightNumber + " | " + fi.id);
        }

        // Pick first result
        long fiId = results.get(0).id;

        // ---------------- Hold Seats ----------------
        System.out.println("\nHolding 2 seats for user 999 ...");
        var holdOpt = inventoryService.holdSeats(999, fiId, 2);

        if (holdOpt.isEmpty()) {
            System.out.println("Cannot hold seats.");
            return;
        }

        SeatHold hold = holdOpt.get();
        System.out.println("  Seats held: " + hold.seatNumbers);
        System.out.println("  Hold ID: " + hold.holdId);

        // ---------------- Confirm Booking ----------------
        System.out.println("\nConfirming booking from hold ...");
        Booking booking = bookingService.confirm(hold.holdId, "Rohit");

        System.out.println("Booking Confirmed!");
        System.out.println("  PNR: " + booking.pnr);
        for (Passenger p : booking.passengers) {
            System.out.println("  Passenger: " + p.name + " | Seat: " + p.seatNumber);
        }

        // ---------------- Retrieve Booking by PNR ----------------
        System.out.println("\nFetching booking by PNR ...");
        Booking fetched = bookingRepo.get(booking.pnr).orElse(null);

        if (fetched != null) {
            System.out.println("Fetched Booking:");
            System.out.println("  PNR: " + fetched.pnr);
            System.out.println("  FlightInstance ID: " + fetched.flightInstanceId);
            System.out.println("  Seats: ");
            fetched.passengers.forEach(
                pax -> System.out.println("    " + pax.seatNumber)
            );
        }
    }
}
