package RideMatchingService.services;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import RideMatchingService.Driver;
import RideMatchingService.Location;
import RideMatchingService.Rider;
import RideMatchingService.Vehicle;
import RideMatchingService.enums.DriverStatus;

public class UserService {
    private Map<String, Rider> riders = new ConcurrentHashMap<>();
    private Map<String, Driver> drivers = new ConcurrentHashMap<>();

    public Rider registerRider(String id, String name, String mobile) {
        Rider rider = new Rider(id, name, mobile);
        riders.put(id, rider);
        return rider;
    }

    public Driver registerDriver(String id, String name, String mobile, Location loc, Vehicle vehicle) {
        Driver driver = new Driver(id, name, mobile, loc, vehicle);
        drivers.put(id, driver);
        return driver;
    }

    public List<Driver> getActiveDrivers() {
        return drivers.values().stream()
                .filter(d -> d.getStatus() == DriverStatus.ACTIVE)
                .collect(Collectors.toList());
    }
}
