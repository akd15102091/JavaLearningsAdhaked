package RailwayBookingSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingService {
    Map<String, Train> trains = new HashMap<>();
    Map<String, Map<String, TrainInstance>> schedule = new HashMap<>();
    // schedule: Map<TrainName, Map<Date, TrainInstance>>

    public void addTrain(String name, List<String> stationNames, int seatCount) {
        trains.put(name, new Train(name, stationNames, seatCount));
    }

    public void addTrainDate(String trainName, String date) {
        Train train = trains.get(trainName);
        if (train == null) throw new IllegalArgumentException("Train not found: " + trainName);
        schedule.computeIfAbsent(trainName, k -> new HashMap<>())
                .put(date, new TrainInstance(train, date));
    }

    public boolean book(String trainName, String date, String passengerName, String from, String to) {
        Map<String, TrainInstance> trainDates = schedule.get(trainName);
        if (trainDates == null || !trainDates.containsKey(date)) {
            System.out.println("❌ No train instance found for " + trainName + " on " + date);
            return false;
        }
        return trainDates.get(date).bookSeat(passengerName, from, to);
    }

    public void showBookings(String trainName, String date) {
        Map<String, TrainInstance> dateMap = schedule.get(trainName);
        if (dateMap == null || !dateMap.containsKey(date)) {
            System.out.println("❌ No bookings found for " + trainName + " on " + date);
            return;
        }

        System.out.println("\n📄 Bookings for " + trainName + " on " + date + ":");
        dateMap.get(date).printBookings();
    }

    public List<TrainInstance> search(String source, String destination, String date) {
        List<TrainInstance> result = new ArrayList<>();

        for (Map.Entry<String, Map<String, TrainInstance>> entry : schedule.entrySet()) {
            TrainInstance instance = entry.getValue().get(date);
            if (instance == null) continue;

            Train train = instance.train;
            try {
                int fromIndex = train.getStationIndex(source);
                int toIndex = train.getStationIndex(destination);
                if (fromIndex < toIndex) {
                    result.add(instance);
                }
            } catch (IllegalArgumentException e) {
                // Skip train if source/destination not in route
            }
        }

        return result;
    }
}
