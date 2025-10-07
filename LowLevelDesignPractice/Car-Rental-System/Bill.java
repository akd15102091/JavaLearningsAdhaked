public class Bill {
    int amount;
    int billNumber;
    boolean isPaid;
    Reservation reservation;

    public Bill() {
    }

    

    public Bill(Reservation reservation) {
        this.reservation = reservation;
        this.isPaid = false;
        this.amount = this.computeBillAmount() ;

        System.out.println("Bill is generated for reservation with reservationId: "+this.reservation.getReservationId());
    }

    public int computeBillAmount(){
        return 100;
    }

    public int getAmount() {
        return amount;
    }
    public int getBillNumber() {
        return billNumber;
    }
    
    public boolean isPaid() {
        return isPaid;
    }
    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }



    public Reservation getReservation() {
        return reservation;
    }

    

}
