package DesignRideSharingService;

public class PaymentService {
    public void processPayment(Passenger passenger, Driver driver, double amount) {
        System.out.println("Processed payment of $" + amount + " from " + passenger + " to " + driver);
    }
}
