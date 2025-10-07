package RideMatchingService;

import RideMatchingService.enums.VehicleType;

@SuppressWarnings("unused")
public class Vehicle {
    private String plateNumber;
    private VehicleType type;
    private String model;

    public Vehicle(String plateNumber, VehicleType type, String model) {
        this.plateNumber = plateNumber;
        this.type = type;
        this.model = model;
    }

    public VehicleType getType() { return type; }
}
