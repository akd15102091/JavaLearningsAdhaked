package UberLLDs.CarBookingSystem;

import java.util.List;

public interface CarSelectionStrategy {
    Car selectCar(List<Car> cars, Interval interval);
} 