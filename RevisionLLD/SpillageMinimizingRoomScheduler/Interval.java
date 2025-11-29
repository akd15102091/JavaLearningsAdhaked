package SpillageMinimizingRoomScheduler;

public class Interval implements Comparable<Interval> {
    long start;
    long end;
    
    Interval(long s, long e) {
        this.start = s;
        this.end = e;
    }

    @Override
    public int compareTo(Interval other) {
        if (this.start == other.start) return Long.compare(this.end, other.end);
        return Long.compare(this.start, other.start);
    }
       
}
