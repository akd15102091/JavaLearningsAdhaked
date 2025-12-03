package UberLLDs.EventAssignmentModule;

import java.time.LocalDateTime;

public // Interval for TreeSet (sorted by start, then end)
// ===========================================================
class Interval implements Comparable<Interval> {
    final LocalDateTime start;
    final LocalDateTime end;

    public Interval(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    // Overlap check
    public boolean overlaps(Interval other) {
        return !(this.end.compareTo(other.start) <= 0 ||
                 other.end.compareTo(this.start) <= 0);
    }

    @Override
    public int compareTo(Interval o) {
        int cmp = this.start.compareTo(o.start);
        if (cmp != 0) return cmp;
        return this.end.compareTo(o.end);
    }
}
