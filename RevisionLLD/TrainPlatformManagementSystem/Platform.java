package TrainPlatformManagementSystem;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Platform {
    int id;
    TreeMap<LocalDateTime, ScheduleSlot> schedule = new TreeMap<>();
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public Platform(int id) {
        this.id = id;
    }

    // Check if interval overlaps within this platform
    public boolean isFree(LocalDateTime arrival, LocalDateTime departure) {
        lock.readLock().lock();
        try {
            Map.Entry<LocalDateTime, ScheduleSlot> floor = schedule.floorEntry(arrival);
            if (floor != null && floor.getValue().overlaps(arrival, departure))
                return false;

            Map.Entry<LocalDateTime, ScheduleSlot> ceil = schedule.ceilingEntry(arrival);
            if (ceil != null && ceil.getValue().overlaps(arrival, departure))
                return false;

            return true;
        } finally {
            lock.readLock().unlock();
        }
    }

    public void addSchedule(ScheduleSlot slot) {
        lock.writeLock().lock();
        try {
            schedule.put(slot.arrival, slot);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public String getTrainAt(LocalDateTime time) {
        lock.readLock().lock();
        try {
            Map.Entry<LocalDateTime, ScheduleSlot> floor = schedule.floorEntry(time);
            if (floor != null) {
                ScheduleSlot slot = floor.getValue();
                if (!time.isBefore(slot.arrival) && !time.isAfter(slot.departure))
                    return slot.trainId;
            }
            return null;
        } finally {
            lock.readLock().unlock();
        }
    }
}
