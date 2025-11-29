package SpillageMinimizingRoomScheduler;

import java.time.Duration;
import java.time.LocalDateTime;

public class Interval implements Comparable<Interval> {
    LocalDateTime start;
    LocalDateTime end;

    Interval(LocalDateTime s, LocalDateTime e) {
        this.start = s;
        this.end = e;
    }

    boolean overlaps(Interval other) {
        return this.start.isBefore(other.end) && this.end.isAfter(other.start);
    }

    boolean contains(Interval other) {
        return (this.start.isEqual(other.start) || this.start.isBefore(other.start))
                && (this.end.isEqual(other.end) || this.end.isAfter(other.end));
    }

    long durationMinutes() {
        return Duration.between(start, end).toMinutes();
    }

    @Override
    public int compareTo(Interval o) {
        int cmp = this.start.compareTo(o.start);
        if (cmp != 0) return cmp;
        return this.end.compareTo(o.end);
    }

    @Override
    public String toString() {
        return "[" + start + " → " + end + "]";
    }
       
}
