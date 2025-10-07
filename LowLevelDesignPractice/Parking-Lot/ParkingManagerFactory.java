import java.util.List;

public class ParkingManagerFactory {
    ParkingManager twoWheelerParkingManager;
    ParkingManager fourWheelerParkingManager;

    public void setTwoWheelerParkingManager(List<ParkingSpot> parkingSpots){
        this.twoWheelerParkingManager = new TwoWheelerParkingManager(parkingSpots); 
    }
    public void setFourWheelerParkingManager(List<ParkingSpot> parkingSpots){
        this.fourWheelerParkingManager = new FourWheelerParkingManager(parkingSpots); ;
    }
    
    public ParkingManager getParkingManager(VehicleType vehicleType){
        if(vehicleType == VehicleType.TWO_WHEELER){
            return twoWheelerParkingManager ;
        }
        else{
            return fourWheelerParkingManager;
        }
    }
}
