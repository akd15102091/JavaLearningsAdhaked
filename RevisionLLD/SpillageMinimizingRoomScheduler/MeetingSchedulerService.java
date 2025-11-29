package SpillageMinimizingRoomScheduler;

import java.util.List;

public interface MeetingSchedulerService {
    boolean scheduleMeeting(Meeting meeting);
    List<AuditLog> getRoomAuditLogs(String roomId);
}
