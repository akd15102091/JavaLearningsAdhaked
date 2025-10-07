package DesignCarRentalSystem.Payment;

// Singleton Pattern: PaymentProcessor
public class PaymentProcessor {
    private static PaymentProcessor instance;
    private PaymentStrategy paymentStrategy;

    private PaymentProcessor() {}

    public static PaymentProcessor getInstance() {
        if (instance == null) {
            instance = new PaymentProcessor();
        }
        return instance;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public boolean processPayment(double amount) {
        if (paymentStrategy == null) {
            throw new IllegalStateException("Payment strategy not set");
        }
        return paymentStrategy.processPayment(amount);
    }
}
