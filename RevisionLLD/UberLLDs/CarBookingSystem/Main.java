package UberLLDs.CarBookingSystem;

public class Main {
    public static void main(String[] args) {
        // Start with First-Fit strategy
        CarSelectionStrategy firstFit = new FirstFitStrategy();
        CarBookingService service = new CarBookingService(firstFit);

        // Add some cars
        service.addCar(new Car(1));
        service.addCar(new Car(2));
        service.addCar(new Car(3));

        System.out.println("=== Initial availability for [10,12) ===");
        System.out.println("Available cars: " + service.getAllAvailableCars(10, 12));

        System.out.println("\nReserving a car for [10,12): " + service.reserveCar(10, 12));
        System.out.println("Availability for [10,12) after booking: " +
                service.getAllAvailableCars(10, 12));

        System.out.println("\nTrying to reserve overlapping [11,13): " +
                service.reserveCar(11, 13)); // might still succeed on another car

        System.out.println("\nAvailability for [10,12): " +
                service.getAllAvailableCars(10, 12));

        // Switch strategy at runtime (just to show pluggability)
        service.setStrategy(new MinimumWastageStrategy());
        System.out.println("\nReserving using MinimumWastageStrategy for [20,25): " +
                service.reserveCar(20, 25));

        System.out.println("\nFinal availability for [20,25): " +
                service.getAllAvailableCars(20, 25));
    }
}
