package RideMatchingService;

import java.util.concurrent.atomic.AtomicReference;

import RideMatchingService.enums.DriverStatus;

public class Driver extends User {
    private AtomicReference<DriverStatus> status = new AtomicReference<>(DriverStatus.ACTIVE);
    private Location currentLocation;
    private Vehicle vehicle;

    public Driver(String userId, String name, String mobile, Location location, Vehicle vehicle) {
        super(userId, name, mobile);
        this.currentLocation = location;
        this.vehicle = vehicle;
    }

    public Location getCurrentLocation() { return currentLocation; }
    public void setCurrentLocation(Location loc) { this.currentLocation = loc; }

    public Vehicle getVehicle() { return vehicle; }

    public DriverStatus getStatus() { return status.get(); }

    // CAS to ensure one ride per driver
    public boolean setStatus(DriverStatus expected, DriverStatus newStatus) {
        return status.compareAndSet(expected, newStatus);
    }
}