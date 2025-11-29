package SpillageMinimizingRoomScheduler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeetingSchedulerServiceImpl implements MeetingSchedulerService{

    private final Map<String, Room> rooms;
    private final AuditLogManager auditLogManager;

    public MeetingSchedulerServiceImpl(List<Room> roomList) {
        rooms = new HashMap<>();
        for (Room r : roomList) rooms.put(r.roomId, r);
        this.auditLogManager = new AuditLogManager();
    }

    public AuditLogManager getAuditManager() {
        return auditLogManager;
    }

    @Override
    public boolean scheduleMeeting(Meeting meeting) {
        Room bestRoom = null;
        long bestSpillage = Long.MAX_VALUE;

        // 1. find best room (minimize spillage)
        for (Room room : rooms.values()) {

            if (room.capacity < meeting.requiredCapacity) continue;

            room.lock.readLock().lock();
            try {
                Interval free = getFreeWindow(room, meeting.startTime, meeting.endTime);
                if (free == null) continue;

                long windowSize = free.end - free.start;
                long meetingSize = meeting.endTime - meeting.startTime;
                long spillage = windowSize - meetingSize;

                if (spillage < bestSpillage) {
                    bestSpillage = spillage;
                    bestRoom = room;
                }

            } finally {
                room.lock.readLock().unlock();
            }
        }

        if (bestRoom == null) return false;

        // 2. write lock → final booking
        return addMeetingToRoom(bestRoom, meeting);
    }

    private boolean addMeetingToRoom(Room room, Meeting m) {
        room.lock.writeLock().lock();
        try {
            // recheck to avoid race condition
            if (!isAvailable(room, m.startTime, m.endTime)) return false;

            room.bookings.add(new Interval(m.startTime, m.endTime));
            auditLogManager.log(room.roomId, m.meetingId);
            return true;

        } finally {
            room.lock.writeLock().unlock();
        }
    }

    // Checks if interval overlaps with existing
    private boolean isAvailable(Room room, long start, long end) {
        Interval floor = room.bookings.floor(new Interval(start, end));
        Interval ceil = room.bookings.ceiling(new Interval(start, end));

        if (floor != null && floor.end > start) return false;
        if (ceil != null && ceil.start < end) return false;

        return true;
    }

    // compute free window that fully covers meeting
    private Interval getFreeWindow(Room room, long start, long end) {
        if (!isAvailable(room, start, end)) return null;

        Interval floor = room.bookings.floor(new Interval(start, end));
        Interval ceil = room.bookings.ceiling(new Interval(start, end));

        long windowStart = (floor == null) ? Long.MIN_VALUE : floor.end;
        long windowEnd = (ceil == null) ? Long.MAX_VALUE : ceil.start;

        if (windowStart <= start && windowEnd >= end) {
            return new Interval(windowStart, windowEnd);
        }
        return null;
    }

    @Override
    public List<AuditLog> getRoomAuditLogs(String roomId) {
        // Delegate to AuditLogManager
        return auditLogManager.getLogsForRoom(roomId);
    }
    
}
