package DesignMeetingSchedular;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class DemoMain {
    public static void main(String[] args) {
        SchedulerService svc = new SchedulerService();

        // add users
        svc.addUser(new User("U1", "Alice"));
        svc.addUser(new User("U2", "Bob"));

        // add rooms
        svc.addRoom(new MeetingRoom("R1", "Conf-A", 4, new HashSet<>(Arrays.asList("Projector"))));
        svc.addRoom(new MeetingRoom("R2", "Huddle-B", 2, new HashSet<>(Arrays.asList("TV"))));

        // timeslots
        TimeSlot t1 = new TimeSlot(LocalDateTime.of(2025,8,12,10,0), LocalDateTime.of(2025,8,12,11,0));
        TimeSlot t2 = new TimeSlot(LocalDateTime.of(2025,8,12,10,30), LocalDateTime.of(2025,8,12,11,30));

        // schedule meeting in R1 (Alice + Bob)
        String m1 = svc.scheduleMeeting("Design", t1, Arrays.asList("U1", "U2"), "R1");
        System.out.println("Scheduled " + m1);

        // Schedule overlapping meeting for Alice in different room -> allowed
        String m2 = svc.scheduleMeeting("OneOnOne", t2, Arrays.asList("U1"), "R2");
        System.out.println("Scheduled " + m2 + " (Alice overlaps allowed)");

        // Attempt to schedule in R1 overlapping -> should fail
        try {
            svc.scheduleMeeting("Conflict", t2, Arrays.asList("U2"), "R1");
            System.out.println("Unexpectedly scheduled conflicting meeting in R1");
        } catch (Exception e) {
            System.out.println("Expected failure: " + e.getMessage());
        }

        // Query busy slots for Alice on date
        LocalDate date = LocalDate.of(2025,8,12);
        List<TimeSlot> busy = svc.getBusySlotsForUser("U1", date);
        System.out.println("Alice busy on " + date + ": " + busy);

        // Working hours 9:00-18:00
        TimeSlot workingHours = new TimeSlot(LocalDateTime.of(2025,8,12,9,0), LocalDateTime.of(2025,8,12,18,0));
        List<TimeSlot> free = svc.getFreeSlotsForUser("U1", date, workingHours);
        System.out.println("Alice free on " + date + " within working hours: " + free);

        // Room schedule
        System.out.println("R1 schedule: " + svc.getScheduleForRoom("R1"));
    }
}

