package UberLLDs.EventAssignmentModule;

import java.time.LocalDateTime;

public class Event {
    final LocalDateTime start;
    final LocalDateTime end;
    final int requiredCapacity;

    public Event(LocalDateTime start, LocalDateTime end, int requiredCapacity) {
        if (start.isAfter(end) || start.equals(end)) {
            throw new IllegalArgumentException("Invalid event interval");
        }
        this.start = start;
        this.end = end;
        this.requiredCapacity = requiredCapacity;
    }
}
