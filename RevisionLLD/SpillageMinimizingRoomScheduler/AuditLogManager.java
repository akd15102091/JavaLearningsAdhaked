package SpillageMinimizingRoomScheduler;

import java.util.*;

public class AuditLogManager {
    private static final long TTL_DAYS = 7;

    private final PriorityQueue<AuditLog> pq =
            new PriorityQueue<>(Comparator.comparingLong(a -> a.timestamp));

    public synchronized void log(String roomId, String meetingId) {
        pq.offer(new AuditLog(meetingId, roomId, System.currentTimeMillis()));
    }

    public synchronized void cleanup() {
        long limit = System.currentTimeMillis() - TTL_DAYS * 24 * 3600 * 1000;
        while (!pq.isEmpty() && pq.peek().timestamp < limit) {
            pq.poll();
        }
    }

    public synchronized List<AuditLog> getLogsForRoom(String roomId) {
        List<AuditLog> res = new ArrayList<>();
        for (AuditLog log : pq) {
            if (log.roomId.equals(roomId)) res.add(log);
        }
        return res;
    }

    public synchronized void dumpAll() {
        System.out.println("---- All Audit Logs ----");
        for (AuditLog log : pq) {
            System.out.println("Meeting " + log.meetingId +
                    " → Room " + log.roomId +
                    " at " + new Date(log.timestamp));
        }
    }
}
