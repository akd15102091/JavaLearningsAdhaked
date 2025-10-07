package RideMatchingService;

import java.util.UUID;

import RideMatchingService.enums.RideStatus;

public class Ride {
    private String rideId;
    private Rider rider;
    private Driver driver;
    private Location pickup;
    private Location drop;
    private double fare;
    private RideStatus status;
    private boolean isPaymentDone;

    public Ride(Rider rider, Location pickup, Location drop, double fare) {
        this.rideId = UUID.randomUUID().toString();
        this.rider = rider;
        this.pickup = pickup;
        this.drop = drop;
        this.fare = fare;
        this.status = RideStatus.REQUESTED;
        this.isPaymentDone = false;
    }

    public String getRideId() { return rideId; }
    public Rider getRider() { return rider; }
    public Driver getDriver() { return driver; }
    public void setDriver(Driver driver) { this.driver = driver; this.status = RideStatus.DRIVER_ASSIGNED; }
    public Location getPickup() { return pickup; }
    public Location getDrop() { return drop; }
    public double getFare() { return fare; }
    public RideStatus getStatus() { return status; }
    public void setStatus(RideStatus status) { this.status = status; }
    public boolean isPaymentDone() { return isPaymentDone; }
    public void markPaymentDone() { this.isPaymentDone = true; }
}
