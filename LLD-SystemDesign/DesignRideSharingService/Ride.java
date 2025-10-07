package DesignRideSharingService;

import DesignRideSharingService.RideTypeFactory.RideType;

@SuppressWarnings("unused")
public class Ride {
    private static int counter = 1;
    private final int id;
    private final Passenger passenger;
    private final Location pickup;
    private final Location destination;
    private final RideType rideType;
    private Driver driver;

    public Ride(Passenger passenger, Location pickup, Location destination, RideType rideType) {
        this.id = counter++;
        this.passenger = passenger;
        this.pickup = pickup;
        this.destination = destination;
        this.rideType = rideType;
    }

    public int getId() {
        return id;
    }

    public Location getPickup() {
        return pickup;
    }

    public void assignDriver(Driver driver) {
        this.driver = driver;
        passenger.update("Your ride is assigned to " + driver.toString());
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Driver getDriver() {
        return driver;
    }

    public double calculateFare() {
        double dist = pickup.distanceTo(destination) ;
        return rideType.calculateFare(dist, 10) ;
    }
}
