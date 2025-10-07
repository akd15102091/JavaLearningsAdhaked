import java.util.*;

public class VehicleInventory {
    List<Vehicle>vehicles ;

    public List<Vehicle> getVehicles(){
        return vehicles;
    }

    public List<Vehicle> getStoreVehiclesByType(VehicleType vehicleType){
        List<Vehicle> resultVehicles =new ArrayList<>();
        for(int i=0;i<vehicles.size();i++){
            if(vehicles.get(i).vehicleType == vehicleType){
                resultVehicles.add(vehicles.get(i));
            }
        }
        return resultVehicles;
    }
    public VehicleInventory(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public void addVehicleToStore(Vehicle vehicle){
        vehicles.add(vehicle);
    }
    // update and delete vehicle to store 
}
