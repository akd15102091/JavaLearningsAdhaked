package TrainPlatformManagementSystem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TrainPlatformService {
    private List<Platform> platforms = new ArrayList<>();
    private Map<String, Platform> trainToPlatform = new ConcurrentHashMap<>();

    public TrainPlatformService(int platformCount) {
        for (int i = 1; i <= platformCount; i++) {
            platforms.add(new Platform(i));
        }
    }

    public synchronized int assignTrain(String trainId, LocalDateTime arrival, LocalDateTime departure) {
        for (Platform p : platforms) {
            if (p.isFree(arrival, departure)) {
                ScheduleSlot slot = new ScheduleSlot(trainId, arrival, departure);
                p.addSchedule(slot);
                trainToPlatform.put(trainId, p);
                return p.id;
            }
        }
        throw new RuntimeException("No platform free!");
    }

    public String getTrainAtPlatform(int platformId, LocalDateTime time) {
        Platform p = platforms.get(platformId - 1);
        return p.getTrainAt(time);
    }

    public Integer getPlatformOfTrain(String trainId, LocalDateTime time) {
        Platform p = trainToPlatform.get(trainId);
        if (p == null) return null;

        String t = p.getTrainAt(time);
        return (t != null) ? p.id : null;
    }
}
