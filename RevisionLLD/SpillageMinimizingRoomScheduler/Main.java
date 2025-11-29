package SpillageMinimizingRoomScheduler;

import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        // ---- Create Rooms ----
        Room r1 = new Room("Room1", 5);
        Room r2 = new Room("Room2", 10);

        // Pre-book sample intervals
        r1.bookings.add(new Interval(
                LocalDateTime.of(2025, 11, 29, 8, 0),
                LocalDateTime.of(2025, 11, 29, 9, 0)
        ));

        r2.bookings.add(new Interval(
                LocalDateTime.of(2025, 11, 29, 12, 0),
                LocalDateTime.of(2025, 11, 29, 14, 0)
        ));

        // List of rooms
        List<Room> rooms = Arrays.asList(r1, r2);

        // ---- Create Scheduler ----
        MeetingSchedulerServiceImpl scheduler =
                new MeetingSchedulerServiceImpl(rooms);

        System.out.println("\n==== Scheduling ====");

        Meeting m1 = new Meeting(
                "M1",
                new Interval(
                        LocalDateTime.of(2025, 11, 29, 9, 0),
                        LocalDateTime.of(2025, 11, 29, 10, 0)),
                4
        );
        System.out.println("Booking M1 → " + scheduler.scheduleMeeting(m1));

        Meeting m2 = new Meeting(
                "M2",
                new Interval(
                        LocalDateTime.of(2025, 11, 29, 10, 0),
                        LocalDateTime.of(2025, 11, 29, 11, 0)),
                6
        );
        System.out.println("Booking M2 → " + scheduler.scheduleMeeting(m2));

        Meeting m3 = new Meeting(
                "M3",
                new Interval(
                        LocalDateTime.of(2025, 11, 29, 9, 0),
                        LocalDateTime.of(2025, 11, 29, 12, 0)),
                8
        );
        System.out.println("Booking M3 → " + scheduler.scheduleMeeting(m3));

        // ---- Final Bookings ----
        System.out.println("\n==== Final Room Bookings ====");
        printRoom(r1);
        printRoom(r2);

        // ---- Audit Logs ----
        System.out.println("\n==== Audit Logs ====");
        scheduler.getAuditManager().dumpAll();
    }

    private static void printRoom(Room room) {
        System.out.println(room.roomId + " bookings:");
        for (Interval i : room.bookings) {
            System.out.println("  " + i);
        }
    }
}
