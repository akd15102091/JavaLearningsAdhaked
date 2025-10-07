import java.util.ArrayList;
import java.util.List;

public class Store {
    int id;
    String storeName;
    List<Reservation> reservationList = new ArrayList<>();
    String location;
    VehicleInventory inventory;
    public Store(int id, String storeName, String location) {
        this.id = id;
        this.storeName = storeName;
        this.location = location;
    }

    public Store() {
    }
 
    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Store [id=" + id + ", storeName=" + storeName + ", reservationList=" + reservationList + ", location="
                + location + ", inventory=" + inventory + "]";
    }

    public void setVehicle(List<Vehicle> vehicle){
         this.inventory = new VehicleInventory(vehicle);
    }
    public List<Vehicle> getVehicles(){
        return inventory.getVehicles();
    }
    public void addReservation(Reservation reservation){
        reservationList.add(reservation);
    }

    public List<Vehicle> getStoreVehiclesByType(VehicleType vehicleType){
        System.out.println("Fetch store vehicles with vehicleType "+vehicleType);
        return inventory.getStoreVehiclesByType(vehicleType);
    }

    public Reservation createReservation(Vehicle vehicle, User user){
        Reservation reservation = new Reservation() ;
        reservation.createReserve(vehicle, user); 
        this.reservationList.add(reservation) ;

        return reservation;
    }
    
    public void completeReservation(String reservationId){
        for(int i=0;i<reservationList.size();i++){
            if(reservationList.get(i).getReservationId() == reservationId){
                reservationList.get(i).completeReservation() ;
            }
        }


    }
}
