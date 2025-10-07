package RailwayBookingSystem;

import java.util.ArrayList;
import java.util.List;

public class Train {
    String name;
    List<Station> stations = new ArrayList<>();
    int totalSeats;

    public Train(String name, List<String> stationNames, int totalSeats) {
        this.name = name;
        this.totalSeats = totalSeats;
        for (String station : stationNames) {
            this.stations.add(new Station(station));
        }
    }

    public int getStationIndex(String stationName) {
        for (int i = 0; i < stations.size(); i++) {
            if (stations.get(i).name.equals(stationName)) return i;
        }
        throw new IllegalArgumentException("Station not found: " + stationName);
    }
}
