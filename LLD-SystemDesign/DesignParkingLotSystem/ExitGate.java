package DesignParkingLotSystem;

public class ExitGate {
    private final ParkingLot parkingLot;
    public ExitGate(ParkingLot lot) {
        this.parkingLot = lot;
    }
    
    public void processExit(ParkingSpot spot) {
        parkingLot.releaseVehicle(spot);
    }
}
