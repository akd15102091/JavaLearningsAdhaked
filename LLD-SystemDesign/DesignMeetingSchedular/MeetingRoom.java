package DesignMeetingSchedular;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.List;
import java.util.Objects;

/**
 * Meeting room resource with a lock and a thread-safe list of meetings in that room.
 */
public class MeetingRoom {
    private final String roomId;
    private final String name;
    private final int capacity;
    private final Set<String> features;
    private final ReentrantLock lock = new ReentrantLock();
    private final List<Meeting> meetings = new CopyOnWriteArrayList<>();

    public MeetingRoom(String roomId, String name, int capacity, Set<String> features) {
        this.roomId = Objects.requireNonNull(roomId, "roomId cannot be null");
        this.name = Objects.requireNonNull(name, "name cannot be null");
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be > 0");
        this.capacity = capacity;
        this.features = features; // may be null or empty
    }

    public String getRoomId() { return roomId; }
    public String getName() { return name; }
    public int getCapacity() { return capacity; }
    public Set<String> getFeatures() { return features; }
    public ReentrantLock getLock() { return lock; }

    /**
     * Check availability for the given timeslot (no locking here; caller should hold lock when needed).
     */
    public boolean isAvailable(TimeSlot slot) {
        Objects.requireNonNull(slot, "slot cannot be null");
        for (Meeting m : meetings) {
            if (m.getTimeSlot().isOverlaps(slot)) return false;
        }
        return true;
    }

    public void addMeeting(Meeting meeting) {
        meetings.add(Objects.requireNonNull(meeting, "meeting cannot be null"));
    }

    public void removeMeeting(String meetingId) {
        meetings.removeIf(m -> m.getMeetingId().equals(meetingId));
    }

    public List<Meeting> getMeetingsSnapshot() {
        return List.copyOf(meetings);
    }

    @Override
    public String toString() {
        return "Room{" + roomId + ", " + name + ", cap=" + capacity + "}";
    }
}

