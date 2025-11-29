package SpillageMinimizingRoomScheduler;

import java.util.Arrays;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        // ---- Create Rooms ----
        Room r1 = new Room("Room1", 5);
        Room r2 = new Room("Room2", 10);

        // Pre-book ranges to create different free windows
        r1.bookings.add(new Interval(8, 9));   // Room1 free 9–∞
        r2.bookings.add(new Interval(12, 14)); // Room2 free 0–12, 14–∞

        List<Room> rooms = Arrays.asList(r1, r2);

        // ---- Create Scheduler ----
        MeetingSchedulerServiceImpl scheduler =
                new MeetingSchedulerServiceImpl(rooms);

        System.out.println("\n==== Scheduling ====");

        Meeting m1 = new Meeting("M1", 9, 10, 4);
        System.out.println("Scheduling M1 → " + scheduler.scheduleMeeting(m1));

        Meeting m2 = new Meeting("M2", 10, 11, 6);
        System.out.println("Scheduling M2 → " + scheduler.scheduleMeeting(m2));

        Meeting m3 = new Meeting("M3", 9, 12, 8);
        System.out.println("Scheduling M3 → " + scheduler.scheduleMeeting(m3));

        // ---- Print Bookings ----
        System.out.println("\n==== Final Room Bookings ====");
        printRoom(r1);
        printRoom(r2);

        // ---- Audit Logs ----
        System.out.println("\n==== All Logs ====");
        scheduler.getAuditManager().dumpAll();

        // ---- Room-specific logs (getRoomAuditLogs) ----
        System.out.println("\n==== Logs for Room1 ====");
        List<AuditLog> room1Logs = scheduler.getRoomAuditLogs("Room1");
        for (AuditLog log : room1Logs) {
            System.out.println("Meeting " + log.meetingId +
                    " in " + log.roomId +
                    " at " + new Date(log.timestamp));
        }
    }

    private static void printRoom(Room room) {
        System.out.println(room.roomId + " bookings:");
        for (Interval i : room.bookings) {
            System.out.println("  [" + i.start + ", " + i.end + "]");
        }
    }
}
