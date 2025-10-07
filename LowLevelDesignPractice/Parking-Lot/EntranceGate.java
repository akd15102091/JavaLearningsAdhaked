public class EntranceGate {
    Ticket ticket ;
    ParkingManagerFactory parkingManagerFactory;

    // public EntranceGate(Ticket ticket, ParkingManager parkingManager) {
    //     this.ticket = ticket;
    //     this.parkingManager = parkingManager;
    // }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public void setParkingManagerFactory(ParkingManagerFactory parkingManagerFactory) {
        this.parkingManagerFactory = parkingManagerFactory;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void bookSpot(Vehicle vehicle){
        ParkingManager parkingManager = this.parkingManagerFactory.getParkingManager(vehicle.getVehicleType()) ;
        ParkingSpot freeParkingSpot = parkingManager.findSpace();
        if(freeParkingSpot != null){
            parkingManager.parkVehicle(vehicle, freeParkingSpot);
            //ticket generate
            generateTicket(vehicle, freeParkingSpot) ;
        }   
        else{
            System.out.println("Oops, parking full.");
        }
    }

    public void generateTicket(Vehicle vehicle, ParkingSpot freeParkingSpot){
        Ticket ticket = new Ticket(vehicle, System.currentTimeMillis(), freeParkingSpot) ;
        this.setTicket(ticket);
    }

}
