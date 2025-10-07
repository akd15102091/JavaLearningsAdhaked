package RideMatchingService.services;

import RideMatchingService.Location;
import RideMatchingService.enums.VehicleType;

public class FareCalculatorService {
    // basic estimation based on distance and vehicle type
    public double estimateFare(Location pickup, Location drop, VehicleType type) {
        double distance = Math.sqrt(Math.pow(pickup.getLatitude()-drop.getLatitude(),2)+
                                    Math.pow(pickup.getLongitude()-drop.getLongitude(),2));
        double baseFare;
        switch(type) {
            case BIKE: baseFare = 5; break;
            case AUTO: baseFare = 10; break;
            case SEDAN: baseFare = 15; break;
            case XL: baseFare = 25; break;
            default: baseFare = 10;
        }
        return baseFare + distance*10; // simple distance multiplier
    }
}
