package DesignCarRentalSystem;

// Factory Pattern: CarFactory to create Car objects
public class CarFactory {
    public static Car createCar(String make, String model, int year, String licensePlate, double rentalPricePerDay) {
        return new Car(make, model, year, licensePlate, rentalPricePerDay);
    }
}
