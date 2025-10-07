package DesignParkingLotSystem;

import DesignParkingLotSystem.Vehicle.Vehicle;
import DesignParkingLotSystem.Vehicle.VehicleType;
class ParkingSpot {
    private final VehicleType spotType;
    private Vehicle currentVehicle;
    private final int spotNumber;
    
    public ParkingSpot(VehicleType type, int number) {
        this.spotType = type;
        this.spotNumber = number;
    }
    
    public boolean isAvailable() {
        return currentVehicle == null;
    }
    
    public boolean park(Vehicle vehicle) {
        if (isAvailable() && vehicle.getType() == spotType) {
            currentVehicle = vehicle;
            return true;
        }
        return false;
    }
    
    public void release() {
        currentVehicle = null;
    }

    @Override
    public String toString() {
        return "ParkingSpot [spotType=" + spotType + ", currentVehicle=" + currentVehicle + ", spotNumber=" + spotNumber
                + "]";
    }

    
}
