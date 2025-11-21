package DesignMeetingSchedular;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class Meeting implements Comparable<Meeting>{
    private final String id;
    private final MeetingRoom room;
    private final User organizer;
    private final List<User> participants;
    private final TimeRange range;
    private MeetingStatus status = MeetingStatus.ACTIVE;

    public Meeting(String id, MeetingRoom room, User organizer, List<User> participants, TimeRange range) {
        this.id = id;
        this.room = room;
        this.organizer = organizer;
        this.participants = Collections.unmodifiableList(new ArrayList<>(participants));
        this.range = range;
    }

    public String getId() { return id; }
    public MeetingRoom getRoom() { return room; }
    public List<User> getParticipants() { return participants; }
    public TimeRange getTimeRange() { return range; }
    public MeetingStatus getStatus() { return status; }
    public void cancel() { this.status = MeetingStatus.CANCELLED; }

    @Override
    public int compareTo(Meeting o) {
        int cmp = this.range.getStart().compareTo(o.range.getStart());
        return (cmp != 0) ? cmp : this.id.compareTo(o.id);
    }

    @Override
    public String toString() {
        return "Meeting{id='" + id + "', room=" + room.getId() +
                ", range=" + range + ", status=" + status + "}";
    }
}
