package DesignTrainBookingSystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class TrainInstanceRepository {
    // key by trainId + date
    private final Map<String, TrainInstance> instances = new ConcurrentHashMap<>();

    private String key(String trainId, LocalDate date) { return trainId + "::" + date.toString(); }

    public TrainInstance getOrCreate(Train train, LocalDate date) {
        String k = key(train.trainId, date);
        return instances.computeIfAbsent(k, kk -> new TrainInstance(train, date));
    }


    public Optional<TrainInstance> get(String trainId, LocalDate date) {
        return Optional.ofNullable(instances.get(key(trainId, date)));
    }


    public List<TrainInstance> getAllInstancesForDate(LocalDate date) {
        List<TrainInstance> out = new ArrayList<>();
        for (TrainInstance ti : instances.values()) if (ti.date.equals(date)) out.add(ti);
        return out;
    }
}
