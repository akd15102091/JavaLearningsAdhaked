package AirlineTicketBookingSystem.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import AirlineTicketBookingSystem.FlightInstance;
import AirlineTicketBookingSystem.SeatHold;
import AirlineTicketBookingSystem.SeatStatus;
import AirlineTicketBookingSystem.repository.FlightInstanceRepository;
import AirlineTicketBookingSystem.repository.SeatHoldRepository;

public class InventoryService {
    private final FlightInstanceRepository fiRepo;
    private final SeatHoldRepository holdRepo;

    public InventoryService(FlightInstanceRepository fr, SeatHoldRepository hr) {
        this.fiRepo = fr; this.holdRepo = hr;
    }

    public Optional<SeatHold> holdSeats(long userId, long fiId, int count) {
        FlightInstance fi = fiRepo.get(fiId);

        fi.lock.lock();
        try {
            List<String> free = new ArrayList<>();
            for (var entry : fi.seats.entrySet()) {
                if (entry.getValue() == SeatStatus.AVAILABLE) {
                    free.add(entry.getKey());
                    if (free.size() == count) break;
                }
            }

            if (free.size() < count) return Optional.empty();

            free.forEach(s -> fi.seats.put(s, SeatStatus.HELD));

            SeatHold hold = new SeatHold(
                    UUID.randomUUID().toString(),
                    userId, fiId, free,
                    LocalDateTime.now().plusMinutes(5)
            );

            holdRepo.save(hold);
            return Optional.of(hold);

        } finally {
            fi.lock.unlock();
        }
    }
}
