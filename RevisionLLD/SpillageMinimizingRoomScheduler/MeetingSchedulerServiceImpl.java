package SpillageMinimizingRoomScheduler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeetingSchedulerServiceImpl implements MeetingSchedulerService{

    private final Map<String, Room> rooms;
    private final AuditLogManager auditLogManager;

    public MeetingSchedulerServiceImpl(List<Room> roomList) {
        rooms = new HashMap<>();
        for (Room r : roomList) rooms.put(r.roomId, r);
        auditLogManager = new AuditLogManager();
    }

    @Override
    public boolean scheduleMeeting(Meeting meeting) {

        Room bestRoom = null;
        long bestSpillage = Long.MAX_VALUE;

        // 1️⃣ Choose best room using "min spillage" + capacity fit
        for (Room room : rooms.values()) {

            if (room.capacity < meeting.requiredCapacity) continue;

            room.lock.readLock().lock();
            try {
                Interval free = getFreeWindow(room, meeting.interval);
                if (free == null) continue;

                long freeDuration = Duration.between(free.start, free.end).toMinutes();
                long meetingDuration = meeting.interval.durationMinutes();
                long spillage = freeDuration - meetingDuration;

                if (spillage < bestSpillage) {
                    bestSpillage = spillage;
                    bestRoom = room;
                }

            } finally {
                room.lock.readLock().unlock();
            }
        }

        if (bestRoom == null) return false;

        // 2️⃣ Book under write lock (to avoid concurrency issues)
        return addMeetingToRoom(bestRoom, meeting);
    }

    private boolean addMeetingToRoom(Room room, Meeting meeting) {
        room.lock.writeLock().lock();
        try {
            if (!isAvailable(room, meeting.interval)) return false;

            room.bookings.add(meeting.interval);
            auditLogManager.log(room.roomId, meeting.meetingId);
            return true;

        } finally {
            room.lock.writeLock().unlock();
        }
    }

    /* ============================================================
                      AVAILABILITY CHECK
       ============================================================ */
    private boolean isAvailable(Room room, Interval meetingInterval) {
        Interval floor = room.bookings.floor(meetingInterval);
        Interval ceil = room.bookings.ceiling(meetingInterval);

        if (floor != null && floor.overlaps(meetingInterval)) return false;
        if (ceil != null && ceil.overlaps(meetingInterval)) return false;

        return true;
    }

    /* ============================================================
                     COMPUTE FREE WINDOW AROUND MEETING
       ============================================================ */
    private Interval getFreeWindow(Room room, Interval meetingInterval) {

        if (!isAvailable(room, meetingInterval)) return null;

        Interval floor = room.bookings.floor(meetingInterval);
        Interval ceil = room.bookings.ceiling(meetingInterval);

        LocalDateTime freeStart = (floor == null) ? LocalDateTime.MIN : floor.end;
        LocalDateTime freeEnd   = (ceil == null) ? LocalDateTime.MAX : ceil.start;

        Interval fullWindow = new Interval(freeStart, freeEnd);

        if (fullWindow.contains(meetingInterval)) return fullWindow;
        return null;
    }

    @Override
    public List<AuditLog> getRoomAuditLogs(String roomId) {
        return auditLogManager.getLogsForRoom(roomId);
    }

    public AuditLogManager getAuditManager() {
        return auditLogManager;
    }
    
}
