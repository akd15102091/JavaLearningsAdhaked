package UberLLDs.CarBookingSystem;

import java.util.List;

public class FirstFitStrategy implements CarSelectionStrategy {
    @Override
    public Car selectCar(List<Car> cars, Interval interval) {
        for (Car car : cars) {
            if (car.isAvailable(interval)) {
                return car;
            }
        }
        return null;
    }
}
