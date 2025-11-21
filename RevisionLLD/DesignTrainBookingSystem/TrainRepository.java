package DesignTrainBookingSystem;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

// ---------- Storage + Services ----------
public class TrainRepository {
    private final Map<String, Train> trains = new ConcurrentHashMap<>();


    public void addTrain(Train t) { trains.put(t.trainId, t); }
    public Optional<Train> getTrain(String trainId) { return Optional.ofNullable(trains.get(trainId)); }
    public Collection<Train> allTrains() { return trains.values(); }
}