
public class Vehicle {
    String vehicleName;
    String vehicleId;
    VehicleType vehicleType;
    Status vehicleStatus;
    public Vehicle(String vehicleName, String vehicleId, VehicleType vehicleType, Status vehicleStatus) {
        this.vehicleName = vehicleName;
        this.vehicleId = vehicleId;
        this.vehicleType = vehicleType;
        this.vehicleStatus = vehicleStatus;
    }
    public String getVehicleId() {
        return vehicleId;
    }

    public Status getVehicleStatus() {
        return vehicleStatus;
    }
    public void setVehicleStatus(Status vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }
    public String getVehicleName() {
        return vehicleName;
    }
    
}
