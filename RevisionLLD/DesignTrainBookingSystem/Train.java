package DesignTrainBookingSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Train {
    final String trainId;
    final String name;
    final List<String> stations; // ordered
    final int totalSeats; // number of identical seats (e.g., general coaches)

    public Train(String trainId, String name, List<String> stations, int totalSeats) {
        if (stations == null || stations.size() < 2) throw new IllegalArgumentException("A train must have >=2 stations");
        this.trainId = trainId;
        this.name = name;
        this.stations = Collections.unmodifiableList(new ArrayList<>(stations));
        this.totalSeats = totalSeats;
    }

    public int stationsCount() { return stations.size(); }
    public int indexOfStation(String station) { return stations.indexOf(station); }

    @Override
    public String toString() {
        return "Train{" + trainId + ":" + name + '}';
    }
}
