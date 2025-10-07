import java.util.UUID;

public class Payment {
    Bill bill;
    String transactionId;

    public Payment(Bill bill) {
        this.bill = bill;

        UUID uuid = UUID.randomUUID(); 
        this.transactionId = uuid.toString();
    }

    public boolean payBill(){
        System.out.println("Payment is done for bill with reservationId : "+ this.bill.getReservation().getReservationId());
        this.bill.setPaid(true);
        return true;
    }
}
