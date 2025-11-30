package AirlineTicketBookingSystem.services;

import java.time.LocalDate;
import java.util.List;

import AirlineTicketBookingSystem.FlightInstance;
import AirlineTicketBookingSystem.repository.FlightInstanceRepository;

public class SearchService {
    private final FlightInstanceRepository repo;

    public SearchService(FlightInstanceRepository repo) { this.repo = repo; }

    public List<FlightInstance> search(String origin, String dest, LocalDate date) {
        return repo.search(origin, dest, date);
    }
}
