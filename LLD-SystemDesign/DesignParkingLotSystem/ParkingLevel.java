package DesignParkingLotSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import DesignParkingLotSystem.Vehicle.Vehicle;
import DesignParkingLotSystem.Vehicle.VehicleType;

public class ParkingLevel {
    private final int levelNumber;
    private final List<ParkingSpot> spots;
    private final ReentrantLock lock = new ReentrantLock();

    public ParkingLevel(int levelNumber, int carSpots, int bikeSpots, int truckSpots){
        this.levelNumber = levelNumber;
        this.spots = new ArrayList<>();
        
        int spotId = 1;
        for (int i = 0; i < carSpots; i++) spots.add(new ParkingSpot(VehicleType.CAR, spotId++));
        for (int i = 0; i < bikeSpots; i++) spots.add(new ParkingSpot(VehicleType.BIKE, spotId++));
        for (int i = 0; i < truckSpots; i++) spots.add(new ParkingSpot(VehicleType.TRUCK, spotId++));
    
    }

    public ParkingSpot findSpot(Vehicle vehicle) {
        lock.lock();
        try {
            for (ParkingSpot spot : spots) {
                if (spot.isAvailable() && spot.park(vehicle)) {
                    return spot;
                }
            }
            return null;
        } finally {
            lock.unlock();
        }
    }
    
    public void releaseSpot(ParkingSpot spot) {
        lock.lock();
        try {
            spot.release();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "ParkingLevel : "+levelNumber;
    }

    

}
