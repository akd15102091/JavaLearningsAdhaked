import java.util.UUID;

public class Reservation {
    String reservationId;
    Vehicle vehicle;
    User user;
    Bill reservationBill;
    
    public Reservation() {
    }

    public Reservation(String reservationId, Vehicle vehicle, User user,Bill bill) {
        this.reservationId = reservationId;
        this.vehicle = vehicle;
        this.user = user;
        this.reservationBill = bill;
    }

    public void createReserve(Vehicle vehicle, User user){
        this.vehicle = vehicle;
        this.vehicle.setVehicleStatus(Status.UNAVAILABLE);
        this.user = user;

        UUID uuid = UUID.randomUUID(); 
        this.reservationId = uuid.toString();

        System.out.println("Created a new reservation for user: "+user.getName()+" and Vehicle: "+vehicle.getVehicleName());
    }

    public void completeReservation(){
        // released occupied vehicle
        this.vehicle.setVehicleStatus(Status.AVAILABLE);
        System.out.println("Completed reservation for user: "+user.getName()+" and Vehicle: "+vehicle.getVehicleName());

    }

    public String getReservationId(){
        return this.reservationId;
    }
    
}
