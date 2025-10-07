package DesignVendingMachine.PaymentStrategy;

public class NotePayment implements PaymentStrategy {
    public boolean pay(int amount) {
        System.out.println("Payment done using Notes: " + amount);
        return true;
    }
}
