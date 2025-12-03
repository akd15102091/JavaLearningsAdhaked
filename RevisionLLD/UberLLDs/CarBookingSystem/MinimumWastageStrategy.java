package UberLLDs.CarBookingSystem;

import java.util.List;

public class MinimumWastageStrategy implements CarSelectionStrategy {

    @Override
    public Car selectCar(List<Car> cars, Interval interval) {
        Car bestCar = null;
        int bestWaste = Integer.MAX_VALUE;

        for (Car car : cars) {
            if (!car.isAvailable(interval)) continue;

            // For demo purposes, we can approximate "wastage" as
            // the absolute difference between interval.start and 0
            // In a real system, we'd inspect neighbors for more accurate gaps.
            int waste = Math.abs(interval.start);
            if (waste < bestWaste) {
                bestWaste = waste;
                bestCar = car;
            }
        }
        return bestCar;
    }
}
