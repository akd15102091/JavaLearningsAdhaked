package DesignParkingLotSystem;

import java.util.ArrayList;
import java.util.List;

import DesignParkingLotSystem.Vehicle.Vehicle;

// Singleton class
public class ParkingLot {
    private static ParkingLot instance;
    private final List<ParkingLevel> levels;
    
    private ParkingLot(int numLevels, int carSpots, int bikeSpots, int truckSpots) {
        levels = new ArrayList<>();
        for (int i = 0; i < numLevels; i++) {
            levels.add(new ParkingLevel(i, carSpots, bikeSpots, truckSpots));
        }
    }

    public static synchronized ParkingLot getInstance(int numLevels, int carSpots, int bikeSpots, int truckSpots) {
        if (instance == null) {
            instance = new ParkingLot(numLevels, carSpots, bikeSpots, truckSpots);
        }
        return instance;
    }
    
    public ParkingSpot parkVehicle(Vehicle vehicle) {
        for (ParkingLevel level : levels) {
            ParkingSpot spot = level.findSpot(vehicle);
            if (spot != null) {
                System.out.println("Vehicle parked at Level: " + level.toString() + " Spot: " + spot);
                return spot;
            }
        }
        System.out.println("No available spot for " + vehicle.getType());
        return null;
    }
    
    public void releaseVehicle(ParkingSpot spot) {
        if(spot != null){
            spot.release();
            System.out.println("Vehicle removed from Spot: " + spot);
            return;
        }
        // for (ParkingLevel level : levels) {
        //     if (level.equals(spot)) {
        //         level.releaseSpot(spot);
        //         System.out.println("Vehicle removed from Spot: " + spot);
        //         return;
        //     }
        // }
    }
}
