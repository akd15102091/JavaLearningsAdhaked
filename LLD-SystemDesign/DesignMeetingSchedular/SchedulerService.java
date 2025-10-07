package DesignMeetingSchedular;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SchedulerService orchestrates bookings:
 * - Users may have overlapping meetings.
 * - Rooms cannot be double-booked (room-level lock).
 * - UserCalendar is kept in sync for queries.
 */
public class SchedulerService {
    private final Map<String, User> users = new ConcurrentHashMap<>();
    private final Map<String, MeetingRoom> rooms = new ConcurrentHashMap<>();
    private final Map<String, Meeting> meetings = new ConcurrentHashMap<>();
    private final UserCalendar userCalendar = new UserCalendar();

    // Registration helpers
    public void addUser(User u) {
        Objects.requireNonNull(u, "user cannot be null");
        users.putIfAbsent(u.getUserId(), u);
    }

    public void addRoom(MeetingRoom r) {
        Objects.requireNonNull(r, "room cannot be null");
        rooms.putIfAbsent(r.getRoomId(), r);
    }

    /**
     * Schedule meeting.
     * Enforced constraints:
     *  - room existence & capacity
     *  - room availability (no overlapping meetings in the same room)
     *  - users must exist (but per requirement they can have overlapping meetings)
     *
     * Returns meetingId if successful; throws exception for invalid or conflicting requests.
     */
    public String scheduleMeeting(String title, TimeSlot slot, List<String> participantIds, String roomId) {
        Objects.requireNonNull(title, "title cannot be null");
        Objects.requireNonNull(slot, "slot cannot be null");
        Objects.requireNonNull(participantIds, "participantIds cannot be null");
        if (participantIds.isEmpty()) throw new IllegalArgumentException("participantIds cannot be empty");
        Objects.requireNonNull(roomId, "roomId cannot be null");

        MeetingRoom room = rooms.get(roomId);
        if (room == null) throw new IllegalArgumentException("Room not found: " + roomId);

        // Resolve users
        List<User> participants = new ArrayList<>();
        for (String pid : participantIds) {
            User u = users.get(pid);
            if (u == null) throw new IllegalArgumentException("Participant not found: " + pid);
            participants.add(u);
        }

        if (participants.size() > room.getCapacity()) {
            throw new IllegalArgumentException("Participants exceed room capacity");
        }

        // Lock the room to check availability + create meeting atomically
        room.getLock().lock();
        try {
            if (!room.isAvailable(slot)) {
                throw new IllegalStateException("Room " + roomId + " already booked for the given timeslot");
            }

            Meeting meeting = new Meeting(title, slot, participants, room);
            meetings.put(meeting.getMeetingId(), meeting);

            // Add to room and user calendars
            room.addMeeting(meeting);
            for (User u : participants) {
                userCalendar.addMeeting(u, meeting);
            }

            return meeting.getMeetingId();
        } finally {
            room.getLock().unlock();
        }
    }

    /**
     * Cancel a meeting safely.
     */
    public boolean cancelMeeting(String meetingId) {
        Meeting meeting = meetings.get(meetingId);
        if (meeting == null) return false;

        MeetingRoom room = meeting.getRoom();
        room.getLock().lock();
        try {
            if (meetings.remove(meetingId) == null) return false;
            room.removeMeeting(meetingId);
            for (User u : meeting.getParticipants()) {
                userCalendar.removeMeeting(u, meetingId);
            }
            return true;
        } finally {
            room.getLock().unlock();
        }
    }

    // Query helpers
    public List<TimeSlot> getBusySlotsForUser(String userId, LocalDate date) {
        User u = users.get(userId);
        if (u == null) throw new IllegalArgumentException("User not found: " + userId);
        return userCalendar.getBusySlots(u, date);
    }

    public List<TimeSlot> getFreeSlotsForUser(String userId, LocalDate date, TimeSlot workingHours) {
        User u = users.get(userId);
        if (u == null) throw new IllegalArgumentException("User not found: " + userId);
        return userCalendar.getFreeSlots(u, date, workingHours);
    }

    public List<Meeting> getScheduleForRoom(String roomId) {
        MeetingRoom room = rooms.get(roomId);
        if (room == null) throw new IllegalArgumentException("Room not found: " + roomId);
        List<Meeting> snapshot = room.getMeetingsSnapshot();
        snapshot.sort(Comparator.comparing(m -> m.getTimeSlot().getStartTime()));
        return snapshot;
    }

    public List<Meeting> getMeetingsForUser(String userId) {
        User u = users.get(userId);
        if (u == null) throw new IllegalArgumentException("User not found: " + userId);
        return List.copyOf(userCalendar.getMeetings(u));
    }
}

