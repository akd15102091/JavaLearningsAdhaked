package DesignMeetingSchedular;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * UserCalendar stores meetings per user and offers busy/free-slot queries.
 * Uses thread-safe collections suitable for reads and occasional writes.
 */
@SuppressWarnings("unused")
public class UserCalendar {
    private final Map<String, List<Meeting>> calendar = new ConcurrentHashMap<>();

    public void addMeeting(User user, Meeting meeting) {
        Objects.requireNonNull(user, "user cannot be null");
        Objects.requireNonNull(meeting, "meeting cannot be null");
        calendar.computeIfAbsent(user.getUserId(), k -> new CopyOnWriteArrayList<>()).add(meeting);
    }

    public void removeMeeting(User user, String meetingId) {
        List<Meeting> list = calendar.get(user.getUserId());
        if (list != null) {
            list.removeIf(m -> m.getMeetingId().equals(meetingId));
        }
    }

    public List<Meeting> getMeetings(User user) {
        return calendar.getOrDefault(user.getUserId(), Collections.emptyList());
    }

    /**
     * Returns busy TimeSlots for the given user that intersect the given date.
     * A meeting is considered busy on date D if its TimeSlot intersects [D 00:00, D+1 00:00).
     */
    public List<TimeSlot> getBusySlots(User user, LocalDate date) {
        Objects.requireNonNull(user, "user cannot be null");
        Objects.requireNonNull(date, "date cannot be null");
        List<TimeSlot> busy = new ArrayList<>();
        LocalDateTime dayStart = date.atStartOfDay();
        LocalDateTime dayEnd = dayStart.plusDays(1);
        TimeSlot dayWindow = new TimeSlot(dayStart, dayEnd);

        for (Meeting m : getMeetings(user)) {
            if (m.getTimeSlot().isOverlaps(dayWindow)) {
                // Convert meeting times to the overlapping portion of the day if desired,
                // but for simplicity, return the meeting's timeslot.
                busy.add(m.getTimeSlot());
            }
        }
        // Sort by start time
        busy.sort(Comparator.comparing(ts -> ts.getStartTime()));
        return busy;
    }

    /**
     * Returns free slots for the user within 'workingHours' on the given date.
     * Assumes workingHours covers the required day (i.e., its start/end are on that date).
     */
    public List<TimeSlot> getFreeSlots(User user, LocalDate date, TimeSlot workingHours) {
        Objects.requireNonNull(user, "user cannot be null");
        Objects.requireNonNull(date, "date cannot be null");
        Objects.requireNonNull(workingHours, "workingHours cannot be null");

        List<TimeSlot> busy = getBusySlots(user, date);
        List<TimeSlot> free = new ArrayList<>();

        LocalDateTime cursor = workingHours.getStartTime();

        for (TimeSlot b : busy) {
            // If busy slot ends before working window start or starts after end, skip accordingly
            if (!b.isOverlaps(workingHours)) continue;

            LocalDateTime busyStart = b.getStartTime().isBefore(workingHours.getStartTime()) ? workingHours.getStartTime() : b.getStartTime();
            LocalDateTime busyEnd = b.getEndTime().isAfter(workingHours.getEndTime()) ? workingHours.getEndTime() : b.getEndTime();

            if (cursor.isBefore(busyStart)) {
                free.add(new TimeSlot(cursor, busyStart));
            }
            if (busyEnd.isAfter(cursor)) {
                cursor = busyEnd;
            }
        }

        if (cursor.isBefore(workingHours.getEndTime())) {
            free.add(new TimeSlot(cursor, workingHours.getEndTime()));
        }

        return free;
    }
}

