package TrainPlatformManagementSystem;

import java.time.LocalDateTime;

public class ScheduleSlot {
    String trainId;
    LocalDateTime arrival;
    LocalDateTime departure;

    public ScheduleSlot(String trainId, LocalDateTime arrival, LocalDateTime departure) {
        this.trainId = trainId;
        this.arrival = arrival;
        this.departure = departure;
    }

    public boolean overlaps(LocalDateTime start, LocalDateTime end) {
        return !(end.isBefore(arrival) || start.isAfter(departure));
    }
}
