package DesignRideSharingService.RideTypeFactory;

public class PremiumRide implements RideType{
    @Override
    public double calculateFare(double distance, double time) {
        return 20 + (distance * 3) + (time * 2);
    }
}
