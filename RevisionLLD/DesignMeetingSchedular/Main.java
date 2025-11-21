package DesignMeetingSchedular;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MeetingScheduler scheduler = new MeetingScheduler();

        MeetingRoom room = new MeetingRoom("R1", "Conf Room", 10);
        User alice = new User("U1", "Alice");
        User bob = new User("U2", "Bob");

        LocalDateTime now = LocalDateTime.now();
        TimeRange t1 = new TimeRange(now, now.plusMinutes(30));
        TimeRange t2 = new TimeRange(now.plusMinutes(10), now.plusMinutes(40));

        // Successful booking
        String m1 = scheduler.bookMeeting(room, alice, List.of(alice, bob), t1);
        System.out.println("Booked: " + m1);

        // Overlapping attempt
        try {
            scheduler.bookMeeting(room, bob, List.of(bob), t2);
        } catch (Exception ex) {
            System.out.println("Expected conflict: " + ex.getMessage());
        }

        // List meetings
        System.out.println("Meetings for room: " +
                scheduler.getMeetingsForRoom("R1", now.minusMinutes(10), now.plusHours(1)));

        // Cancel
        scheduler.cancelMeeting(m1);
        System.out.println("After cancel: " +
                scheduler.getMeetingsForRoom("R1", now.minusMinutes(10), now.plusHours(1)));
    }
}
