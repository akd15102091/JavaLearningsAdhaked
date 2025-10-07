package RideMatchingService.services;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import RideMatchingService.Driver;
import RideMatchingService.Ride;
import RideMatchingService.enums.DriverStatus;
import RideMatchingService.enums.VehicleType;

public class MatchingService {
    private static final int TIMEOUT_MS = 60_000; // 1 min
    private ExecutorService executor = Executors.newCachedThreadPool();

    /*
     * Reason for setting to OFFLINE:
        - It prevents the same driver from being assigned to multiple rides simultaneously.
        - Once a driver is picked for a ride, they are temporarily "unavailable" (OFFLINE) until they complete the ride.
        - This ensures consistency in the ride matching system.
     */
    public boolean assignDriver(Ride ride, List<Driver> drivers, VehicleType type) throws InterruptedException, ExecutionException, TimeoutException{
        System.out.println("Drivers "+drivers.size());
        Callable<Driver> task = () -> {
            for (Driver driver : drivers) {
                if (driver.getVehicle().getType() == type && driver.setStatus(DriverStatus.ACTIVE, DriverStatus.OFFLINE)) {
                    return driver;
                }
            }
            return null;
        };
        Future<Driver> future = executor.submit(task);
        Driver assigned = future.get(TIMEOUT_MS, TimeUnit.MILLISECONDS);
        if (assigned != null) {
            ride.setDriver(assigned);
            return true;
        }
        return false;

    }
}
