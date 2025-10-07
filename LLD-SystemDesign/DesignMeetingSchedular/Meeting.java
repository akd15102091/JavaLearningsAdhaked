package DesignMeetingSchedular;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Meeting entity (immutable core fields).
 * Participants are plain User objects (we store references).
 */
public class Meeting {
    private final String meetingId;
    private final String title;
    private final TimeSlot timeSlot;
    private final List<User> participants;
    private final MeetingRoom room;

    public Meeting(String title, TimeSlot timeSlot, List<User> participants, MeetingRoom room) {
        this.meetingId = UUID.randomUUID().toString();
        this.title = Objects.requireNonNull(title, "title cannot be null");
        this.timeSlot = Objects.requireNonNull(timeSlot, "timeSlot cannot be null");
        this.participants = List.copyOf(Objects.requireNonNull(participants, "participants cannot be null"));
        if (this.participants.isEmpty()) throw new IllegalArgumentException("participants cannot be empty");
        this.room = Objects.requireNonNull(room, "room cannot be null");

        if (this.participants.size() > room.getCapacity()) {
            throw new IllegalArgumentException("participants exceed room capacity");
        }
    }

    public String getMeetingId() { return meetingId; }
    public String getTitle() { return title; }
    public TimeSlot getTimeSlot() { return timeSlot; }
    public List<User> getParticipants() { return participants; }
    public MeetingRoom getRoom() { return room; }

    @Override
    public String toString() {
        return "Meeting{" + meetingId + ", " + title + ", " + timeSlot + ", room=" + room.getRoomId() + "}";
    }
}

