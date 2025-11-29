package SpillageMinimizingRoomScheduler;

public class Meeting {
    String meetingId;
    Interval interval;
    int requiredCapacity;

    Meeting(String id, Interval interval, int cap) {
        this.meetingId = id;
        this.interval = interval;
        this.requiredCapacity = cap;
    }
}
