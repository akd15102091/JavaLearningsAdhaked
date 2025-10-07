package DesignParkingLotSystem;

import DesignParkingLotSystem.Vehicle.Vehicle;

public class EntryGate {
    private final ParkingLot parkingLot;

    public EntryGate(ParkingLot parkingLot){
        this.parkingLot = parkingLot;
    }

    public ParkingSpot processEntry(Vehicle vehicle){
        return this.parkingLot.parkVehicle(vehicle) ;
    }

}
