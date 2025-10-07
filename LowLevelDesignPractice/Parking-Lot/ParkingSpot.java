
public class ParkingSpot{
    Vehicle vehicle;
    boolean isEmpty;
    int id;

    public ParkingSpot(Vehicle vehicle, boolean isEmpty, int id) {
        this.vehicle = vehicle;
        this.isEmpty = isEmpty;
        this.id = id;
    }

    public void setVehicle(Vehicle vehicle){
        this.vehicle = vehicle;
    }

    public void removeVehicle(Vehicle vehicle){
        this.vehicle = null;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public int getId() {
        return id;
    }

    
    
}