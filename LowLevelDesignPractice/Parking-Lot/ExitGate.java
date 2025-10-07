public class ExitGate {
    Ticket ticket;
    ParkingManagerFactory parkingManagerFactory;
    Payment payment;
    CostCalculator costCalculator;

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
    

    public void setParkingManagerFactory(ParkingManagerFactory parkingManagerFactory) {
        this.parkingManagerFactory = parkingManagerFactory;
    }

    public double priceCalc(){
        if(ticket.getVehicle().getVehicleType() == VehicleType.TWO_WHEELER){
            costCalculator = new TwoWheelerCostCalculator(new HourlyBasedStrategy()) ;
            return costCalculator.price(ticket);
        }
        else{ // FOUR_WHEELER
            costCalculator = new FourWheelerCostCalculator(new MinuteBasedStrategy()) ;
            return costCalculator.price(ticket);
        }
    }

    public void doPayment(){
        this.payment = new Payment();
        payment.pay(this.priceCalc()); 
    }

    //removeVehicle
    public void removeVehicle(){
        this.doPayment();
        ParkingManager parkingManager = parkingManagerFactory.getParkingManager(this.ticket.getVehicle().getVehicleType());
        parkingManager.removeVehicle(this.ticket.getParkingSpot()) ;
    }
}
