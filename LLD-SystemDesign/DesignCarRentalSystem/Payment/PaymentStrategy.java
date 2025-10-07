package DesignCarRentalSystem.Payment;

// Strategy Pattern: PaymentStrategy interface and implementations
public interface PaymentStrategy {
    boolean processPayment(double amount);
}