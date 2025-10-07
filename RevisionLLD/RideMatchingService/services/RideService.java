package RideMatchingService.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import RideMatchingService.Driver;
import RideMatchingService.Location;
import RideMatchingService.Ride;
import RideMatchingService.Rider;
import RideMatchingService.enums.RideStatus;
import RideMatchingService.enums.VehicleType;

public class RideService {
    private Map<String, Ride> rides = new ConcurrentHashMap<>();
    private UserService userService;
    private FareCalculatorService fareCalculator;
    private MatchingService matchingService;
    private PaymentService paymentService;

    public RideService(UserService userService, FareCalculatorService fareCalculator,
                       MatchingService matchingService, PaymentService paymentService) {
        this.userService = userService;
        this.fareCalculator = fareCalculator;
        this.matchingService = matchingService;
        this.paymentService = paymentService;
    }

    public Map<VehicleType, Double> getFareEstimates(Location pickup, Location drop) {
        Map<VehicleType, Double> fares = new HashMap<>();
        for(VehicleType type : VehicleType.values()) {
            fares.put(type, fareCalculator.estimateFare(pickup, drop, type));
        }
        return fares;
    }

    public Ride requestRide(Rider rider, Location pickup, Location drop, VehicleType type)
            throws InterruptedException, ExecutionException, TimeoutException {
        double fare = fareCalculator.estimateFare(pickup, drop, type);
        Ride ride = new Ride(rider, pickup, drop, fare);
        rides.put(ride.getRideId(), ride);

        List<Driver> availableDrivers = userService.getActiveDrivers();
        boolean assigned = matchingService.assignDriver(ride, availableDrivers, type);

        if(!assigned) {
            ride.setStatus(RideStatus.CANCELLED);
            throw new RuntimeException("No drivers available. Try again later.");
        }

        return ride;
    }

    public void startRide(Ride ride) { ride.setStatus(RideStatus.STARTED); }

    public void completeRide(Ride ride) {
        ride.setStatus(RideStatus.COMPLETED);
        paymentService.processPayment(ride);
    }
}
