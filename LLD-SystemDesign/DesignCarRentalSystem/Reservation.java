package DesignCarRentalSystem;

import java.util.Date;

public class Reservation {
    private static int counter = 1;
    private final int reservationId;
    private final Car car;
    private final Customer customer;
    private Date startDate;
    private Date endDate;
    private double totalPrice;

    public Reservation(Car car, Customer customer, Date startDate, Date endDate) {
        this.reservationId = counter++;
        this.car = car;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = calculateTotalPrice();
    }

    public boolean overlapsWith(Date start, Date end) {
        return !(end.before(startDate) || start.after(endDate));
    }

    private double calculateTotalPrice() {
        long diff = endDate.getTime() - startDate.getTime();
        int days = (int) (diff / (1000 * 60 * 60 * 24));
        return days * car.getRentalPricePerDay();
    }

    public int getReservationId() {
        return reservationId;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void modifyReservation(Date newStartDate, Date newEndDate) {
        this.startDate = newStartDate;
        this.endDate = newEndDate;
        this.totalPrice = calculateTotalPrice();
    }
}
