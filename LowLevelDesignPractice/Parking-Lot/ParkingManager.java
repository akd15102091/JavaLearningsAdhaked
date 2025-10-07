import java.util.ArrayList;
import java.util.List;

public class ParkingManager {
    List<ParkingSpot> parkingSpotList = new ArrayList<>();

    public ParkingManager(List<ParkingSpot> parkingSpotList) {
        this.parkingSpotList = parkingSpotList;
    }

    // addParkingSpace
    // deleteParkingSpace

    public void removeVehicle(ParkingSpot parkingSpot){
        parkingSpot.setEmpty(true);
        parkingSpot.setVehicle(null); 
        System.out.println("Parking spot with id:"+parkingSpot.getId()+" is empty now.");
    }

    public void parkVehicle(Vehicle vehicle, ParkingSpot parkingSpot){
        parkingSpot.setEmpty(false);
        parkingSpot.setVehicle(vehicle); 
        System.out.println("Parking spot with id:"+parkingSpot.getId()+" is booked now.");
    }

    public ParkingSpot findSpace(){
        for(int i=0;i<parkingSpotList.size();i++){
            if(parkingSpotList.get(i).isEmpty()){
                return parkingSpotList.get(i) ;
            }
        }
        return null;
    }
}
