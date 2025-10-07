package RideMatchingService;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import RideMatchingService.enums.DriverStatus;
import RideMatchingService.enums.VehicleType;
import RideMatchingService.services.FareCalculatorService;
import RideMatchingService.services.MatchingService;
import RideMatchingService.services.PaymentService;
import RideMatchingService.services.RideService;
import RideMatchingService.services.UserService;

public class DriverMain {
    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        UserService userService = new UserService();
        FareCalculatorService fareCalc = new FareCalculatorService();
        PaymentService paymentService = new PaymentService();
        MatchingService matchingService = new MatchingService();
        RideService rideService = new RideService(userService, fareCalc, matchingService, paymentService);

        // register drivers
        Driver d1 = userService.registerDriver("D1", "Alice", "111", new Location(0,0), new Vehicle("P1", VehicleType.SEDAN, "Honda"));
        Driver d2 = userService.registerDriver("D2", "Bob", "222", new Location(0,0), new Vehicle("P2", VehicleType.BIKE, "Suzuki"));
        d1.setStatus(DriverStatus.ACTIVE, DriverStatus.ACTIVE);
        d2.setStatus(DriverStatus.ACTIVE, DriverStatus.ACTIVE);

        // register rider
        Rider r1 = userService.registerRider("R1", "Charlie", "333");

        // fare estimates
        Map<VehicleType, Double> fares = rideService.getFareEstimates(new Location(0,0), new Location(3,4));
        System.out.println("Fare estimates: "+fares);

        // request ride
        Ride ride = rideService.requestRide(r1, new Location(0,0), new Location(3,4), VehicleType.SEDAN);
        System.out.println("Ride assigned to driver: "+ride.getDriver().getName());

        rideService.startRide(ride);
        rideService.completeRide(ride);

        System.out.println("Ride status: "+ride.getStatus()+", Payment done: "+ride.isPaymentDone());
    
    }
}
