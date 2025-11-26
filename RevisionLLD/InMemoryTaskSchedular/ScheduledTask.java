package InMemoryTaskSchedular;

public class ScheduledTask implements Comparable<ScheduledTask>{
    private final Runnable task;
    long nextExecutionTime;       // epoch millis
    private final long intervalMs; // 0 for one-shot tasks

    public ScheduledTask(Runnable task, long nextExecutionTime, long intervalMs) {
        this.task = task;
        this.nextExecutionTime = nextExecutionTime;
        this.intervalMs = intervalMs;
    }

    public boolean isRecurring() {
        return intervalMs > 0;
    }

    public void updateNextExecutionTime() {
        nextExecutionTime += intervalMs;
    }

    public Runnable getTask() {
        return task;
    }

    public long getNextExecutionTime() {
        return nextExecutionTime;
    }

    @Override
    public int compareTo(ScheduledTask other) {
        return Long.compare(this.nextExecutionTime, other.nextExecutionTime);
    }
}
