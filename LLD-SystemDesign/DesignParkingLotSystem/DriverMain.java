package DesignParkingLotSystem;

import DesignParkingLotSystem.Vehicle.Car;
import DesignParkingLotSystem.Vehicle.Vehicle;

public class DriverMain {
    public static void main(String[] args) {
        ParkingLot parkingLot = ParkingLot.getInstance(3, 10, 5, 2);
        EntryGate entryGate = new EntryGate(parkingLot);
        ExitGate exitGate = new ExitGate(parkingLot);
        
        Vehicle car1 = new Car();
        ParkingSpot spot = entryGate.processEntry(car1);
        
        if (spot != null) {
            exitGate.processExit(spot);
        }
    }
}
