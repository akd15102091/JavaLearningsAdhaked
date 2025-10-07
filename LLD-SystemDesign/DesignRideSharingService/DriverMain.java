package DesignRideSharingService;

public class DriverMain {
    public static void main(String[] args) {
        RideSharingService service = RideSharingService.getInstance();
        
        Driver driver1 = new Driver("Alice", new Location(1, 1));
        Driver driver2 = new Driver("Bob", new Location(2, 2));
        
        service.registerDriver(driver1);
        service.registerDriver(driver2);
        
        Passenger passenger = new Passenger("John");
        Ride ride = passenger.requestRide(new Location(0, 0), new Location(5, 5), "regular");
    
        service.completeRide(ride.getId());
    }
}
