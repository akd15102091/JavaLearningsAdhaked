package DesignCarRentalSystem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("unused")
public class Car {
    private final String make;
    private final String model;
    private final int year;
    private final String licensePlate;
    private final double rentalPricePerDay;
    private final List<Reservation> reservations = new ArrayList<>();


    public Car(String make, String model, int year, String licensePlate, double rentalPricePerDay) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.licensePlate = licensePlate;
        this.rentalPricePerDay = rentalPricePerDay;
    }

    public synchronized boolean isAvailable(Date startDate, Date endDate) {
        for (Reservation reservation : reservations) {
            if (reservation.overlapsWith(startDate, endDate)) {
                return false;
            }
        }
        return true;
    }
   

    public synchronized void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public synchronized void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public double getRentalPricePerDay() {
        return rentalPricePerDay;
    }
}
