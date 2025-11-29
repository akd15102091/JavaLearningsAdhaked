package SpillageMinimizingRoomScheduler;

public class Meeting {
    String meetingId;
    long startTime;
    long endTime;
    int requiredCapacity;

    Meeting(String id, long s, long e, int cap) {
        this.meetingId = id;
        this.startTime = s;
        this.endTime = e;
        this.requiredCapacity = cap;
    }
}
