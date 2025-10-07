package DesignRideSharingService.RideTypeFactory;

public class RegularRide implements RideType{

    @Override
    public double calculateFare(double distance, double time) {
        return 10 + (distance * 2) + (time * 1);
    }
    
}
