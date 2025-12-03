package UberLLDs.CarBookingSystem;

import java.util.ArrayList;
import java.util.List;

public class CarBookingService {
    // Shared list of cars. We protect structural changes (add/remove) with synchronization.
    private final List<Car> cars = new ArrayList<>();

    // Pluggable strategy for selecting which car to book
    private CarSelectionStrategy strategy;

    public CarBookingService(CarSelectionStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(CarSelectionStrategy strategy) {
        this.strategy = strategy;
    }

    // Add a new car to the fleet
    public void addCar(Car car) {
        synchronized (cars) {
            cars.add(car);
        }
    }

    // Take a thread-safe snapshot of cars so we don't hold the cars lock while doing heavy work.
    private List<Car> getCarsSnapshot() {
        synchronized (cars) {
            return new ArrayList<>(cars); // copy
        }
    }

    // ------------- API 1: getAllAvailableCars(start, end) -------------
    public List<Car> getAllAvailableCars(int start, int end) {
        Interval req = new Interval(start, end);
        List<Car> snapshot = getCarsSnapshot();
        List<Car> available = new ArrayList<>();

        for (Car car : snapshot) {
            if (car.isAvailable(req)) {
                available.add(car);
            }
        }
        return available;
    }

    // ------------- API 2: reserveCar(start, end) -------------
    public boolean reserveCar(int start, int end) {
        Interval req = new Interval(start, end);
        List<Car> snapshot = getCarsSnapshot();

        // Use strategy to select a car
        Car chosen = strategy.selectCar(snapshot, req);
        if (chosen == null) {
            return false; // No car available
        }

        // Try to reserve. Another thread might sneak in, so this can still fail.
        return chosen.reserve(req);
    }
}
