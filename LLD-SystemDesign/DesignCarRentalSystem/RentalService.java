package DesignCarRentalSystem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import DesignCarRentalSystem.Payment.PaymentProcessor;

public class RentalService {
    private final List<Car> cars = new ArrayList<>();
    private final Map<Integer, Reservation> reservations = new ConcurrentHashMap<>();
    private final ReentrantLock lock = new ReentrantLock();

    public void addCar(Car car) {
        cars.add(car);
    }

    public Reservation createReservation(Car car, Customer customer, Date startDate, Date endDate) {
        lock.lock();
        try {
            if (car.isAvailable(startDate, endDate)) {
                Reservation reservation = new Reservation(car, customer, startDate, endDate);
                PaymentProcessor processor = PaymentProcessor.getInstance();
                if (processor.processPayment(reservation.getTotalPrice())) {
                    car.addReservation(reservation);
                    reservations.put(reservation.getReservationId(), reservation);
                    System.out.println("Reservation successful! Total Price: $" + reservation.getTotalPrice());
                    return reservation;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean cancelReservation(int reservationId) {
        lock.lock();
        try {
            Reservation reservation = reservations.remove(reservationId);
            if (reservation != null) {
                reservation.getCar().removeReservation(reservation);
                System.out.println("Reservation cancelled successfully.");
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public boolean modifyReservation(int reservationId, Date newStartDate, Date newEndDate) {
        lock.lock();
        try {
            Reservation reservation = reservations.get(reservationId);
            if (reservation != null) {
                reservation.modifyReservation(newStartDate, newEndDate);
                System.out.println("Reservation modified successfully. New total price: $" + reservation.getTotalPrice());
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }
}
