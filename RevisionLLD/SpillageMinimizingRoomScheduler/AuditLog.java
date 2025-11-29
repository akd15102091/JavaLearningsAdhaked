package SpillageMinimizingRoomScheduler;

public class AuditLog {
    String meetingId;
    String roomId;
    long timestamp;

    AuditLog(String m, String r, long t) {
        this.meetingId = m;
        this.roomId = r;
        this.timestamp = t;
    }
}
