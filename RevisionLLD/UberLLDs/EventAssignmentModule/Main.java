package UberLLDs.EventAssignmentModule;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        EventAssignmentManager manager = new EventAssignmentManager();

        manager.addRoom(new Room("R1", 5));
        manager.addRoom(new Room("R2", 10));
        manager.addRoom(new Room("R3", 20));

        Event e1 = new Event(
                LocalDateTime.of(2025, 12, 3, 10, 0),
                LocalDateTime.of(2025, 12, 3, 11, 0),
                8);

        Event e2 = new Event(
                LocalDateTime.of(2025, 12, 3, 10, 30),
                LocalDateTime.of(2025, 12, 3, 11, 30),
                4);

        System.out.println(manager.scheduleEvent(e1)); // R2
        System.out.println(manager.scheduleEvent(e2)); // R1 (best fit)
    }
}
