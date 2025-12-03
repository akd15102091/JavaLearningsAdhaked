package UberLLDs.CarBookingSystem;

public class Interval implements Comparable<Interval> {
    final int start;
    final int end;

    Interval(int start, int end) {
        if (start >= end) {
            throw new IllegalArgumentException("start must be < end");
        }
        this.start = start;
        this.end = end;
    }

    boolean overlaps(Interval other) {
        return !(this.end <= other.start || other.end <= this.start);
    }

    /**
    * IMPORTANT:
        * TreeSet uses compareTo() to determine both ordering AND equality.
        *
        * If we compare only by 'start', then two different intervals like:
        *     [10, 12) and [10, 15)
        * would produce compareTo() == 0.
        *
        * TreeSet would treat them as EQUAL keys and silently drop one of them.
        * This is a common and very dangerous bug.
        *
        * To avoid this, we must compare both start AND end.
        *
        * This guarantees:
        *   - Correct ordering
        *   - All intervals remain unique in the TreeSet
    */
    @Override
    public int compareTo(Interval o) {
        if (this.start != o.start) {
            return Integer.compare(this.start, o.start);
        }
        // Must also compare end; otherwise TreeSet treats distinct intervals as duplicates
        return Integer.compare(this.end, o.end);
    }

    @Override
    public String toString() {
        return "[" + start + "," + end + ")";
    }
}
