package DesignRideSharingService;

// Observer Pattern for ride updates
interface RideObserver {
    void update(String status);
}

public class Passenger implements RideObserver {
    private final String name;

    public Passenger(String name) {
        this.name = name;
    }

    public Ride requestRide(Location pickup, Location destination, String rideType) {
        return RideSharingService.getInstance().requestRide(this, pickup, destination, rideType);
    }

    @Override
    public void update(String status) {
        System.out.println(name + " received update: " + status);
    }

    @Override
    public String toString() {
        return "Passenger [name=" + name + "]";
    }
}
