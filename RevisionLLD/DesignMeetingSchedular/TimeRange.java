package DesignMeetingSchedular;

import java.time.LocalDateTime;

public class TimeRange {
private final LocalDateTime start;
    private final LocalDateTime end;

    public TimeRange(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null || !start.isBefore(end)) {
            throw new IllegalArgumentException("Invalid range: " + start + " - " + end);
        }
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart() { return start; }
    public LocalDateTime getEnd() { return end; }

    public boolean overlaps(TimeRange other) {
        return this.start.isBefore(other.end) && other.start.isBefore(this.end);
    }

    @Override
    public String toString() {
        return "[" + start + " to " + end + "]";
    }
}
