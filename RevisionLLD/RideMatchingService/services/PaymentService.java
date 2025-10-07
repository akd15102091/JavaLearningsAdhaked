package RideMatchingService.services;

import RideMatchingService.Ride;

public class PaymentService {
    public void processPayment(Ride ride) {
        // simulate payment
        ride.markPaymentDone();
    }
}
