package AirlineTicketBookingSystem.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import AirlineTicketBookingSystem.Booking;
import AirlineTicketBookingSystem.FlightInstance;
import AirlineTicketBookingSystem.Passenger;
import AirlineTicketBookingSystem.SeatHold;
import AirlineTicketBookingSystem.SeatStatus;
import AirlineTicketBookingSystem.repository.BookingRepository;
import AirlineTicketBookingSystem.repository.FlightInstanceRepository;
import AirlineTicketBookingSystem.repository.SeatHoldRepository;

public class BookingService {
    private final FlightInstanceRepository fiRepo;
    private final SeatHoldRepository holdRepo;
    private final BookingRepository bookingRepo;

    public BookingService(FlightInstanceRepository fr, SeatHoldRepository hr, BookingRepository br) {
        this.fiRepo = fr; this.holdRepo = hr; this.bookingRepo = br;
    }

    public Booking confirm(String holdId, String userName) {
        SeatHold hold = holdRepo.get(holdId)
                .orElseThrow(() -> new RuntimeException("Hold expired/not found"));

        FlightInstance fi = fiRepo.get(hold.flightInstanceId);

        fi.lock.lock();
        try {
            for (String seat : hold.seatNumbers)
                if (fi.seats.get(seat) != SeatStatus.HELD)
                    throw new RuntimeException("Seat lost");

            hold.seatNumbers.forEach(s -> fi.seats.put(s, SeatStatus.BOOKED));

            List<Passenger> pax = new ArrayList<>();
            for (String s : hold.seatNumbers) {
                Passenger p = new Passenger();
                p.name = userName;
                p.seatNumber = s;
                pax.add(p);
            }

            Booking b = new Booking(generatePNR(), fi.id, hold.userId, pax);
            bookingRepo.save(b);
            holdRepo.delete(holdId);

            return b;
        } finally {
            fi.lock.unlock();
        }
    }

    private String generatePNR() {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}
