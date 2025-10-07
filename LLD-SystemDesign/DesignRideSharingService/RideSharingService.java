package DesignRideSharingService;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import DesignRideSharingService.RideTypeFactory.RideFactory;
import DesignRideSharingService.RideTypeFactory.RideType;

// Singleton Pattern - Ride Sharing Service
public class RideSharingService {
    private static RideSharingService instance;
    private final List<Driver> availableDrivers = new CopyOnWriteArrayList<>();
    private final Map<Integer, Ride> ongoingRides = new ConcurrentHashMap<>();

    private final PaymentService paymentService = new PaymentService();

    private RideSharingService() {}

    public static synchronized RideSharingService getInstance() {
        if (instance == null) {
            instance = new RideSharingService();
        }
        return instance;
    }

    public void registerDriver(Driver driver) {
        availableDrivers.add(driver);
    }

    public Ride requestRide(Passenger passenger, Location pickup, Location destination, String rideType) {
        RideType type = RideFactory.getRideType(rideType);
        Ride ride = new Ride(passenger, pickup, destination, type);
        assignDriver(ride);

        return ride;
    }

    private void assignDriver(Ride ride) {
        availableDrivers.stream()
                .min(Comparator.comparing(driver -> driver.getLocation().distanceTo(ride.getPickup())))
                .ifPresent(driver -> {
                    ride.assignDriver(driver);
                    availableDrivers.remove(driver);
                    ongoingRides.put(ride.getId(), ride);
                });
    }

    public void completeRide(int rideId) {
        Ride ride = ongoingRides.remove(rideId);
        if (ride != null) {
            double fare = ride.calculateFare();
            paymentService.processPayment(ride.getPassenger(), ride.getDriver(), fare);
        }
    }
}