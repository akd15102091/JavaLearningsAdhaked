
public class Ticket {
    Vehicle vehicle;
    long entryTime;
    ParkingSpot parkingSpot;

    public Ticket(Vehicle vehicle, long entryTime, ParkingSpot parkingSpot) {
        this.vehicle = vehicle;
        this.entryTime = entryTime;
        this.parkingSpot = parkingSpot;

        System.out.println("Ticket is generated for vehicle:"+vehicle.getVehicleNumber()+" & parking spot id:"+parkingSpot.getId());
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public long getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(long entryTime) {
        this.entryTime = entryTime;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    
}
