package DesignCarRentalSystem;

import java.util.Calendar;
import java.util.Date;

import DesignCarRentalSystem.Payment.CreditCardPayment;
import DesignCarRentalSystem.Payment.PaymentProcessor;

@SuppressWarnings("unused")
public class CarRentalSystem {
    public static void main(String[] args) {
        RentalService rentalService = new RentalService();
        Car car1 = CarFactory.createCar("Toyota", "Camry", 2022, "XYZ123", 50);
        rentalService.addCar(car1);

        Customer customer = new Customer("John Doe", "1234567890", "DL12345");
        Date startDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 5);
        Date endDate = calendar.getTime();

        PaymentProcessor.getInstance().setPaymentStrategy(new CreditCardPayment());
        Reservation reservation = rentalService.createReservation(car1, customer, startDate, endDate);
    }
}
