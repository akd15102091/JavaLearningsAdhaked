package DesignParkingLotSystem.Vehicle;

abstract public class Vehicle {
    protected VehicleType type;
    public VehicleType getType() {
        return type;
    }
    @Override
    public String toString() {
        return "Vehicle [type=" + type + "]";
    }
}
