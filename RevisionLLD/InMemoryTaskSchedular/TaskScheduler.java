package InMemoryTaskSchedular;

import java.time.Instant;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskScheduler {
    private final PriorityQueue<ScheduledTask> pq = new PriorityQueue<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(4);
    private final Object lock = new Object();
    private volatile boolean running = true;

    public TaskScheduler() {
        Thread worker = new Thread(this::runScheduler);
        worker.setDaemon(true);
        worker.start();
    }

    // ----------- Public APIs --------------

    public void schedule(Runnable task, Instant time) {
        long millis = time.toEpochMilli();
        ScheduledTask st = new ScheduledTask(task, millis, 0);
        addTask(st);
    }

    public void scheduleAtFixedInterval(Runnable task, long intervalSeconds) {
        long now = System.currentTimeMillis();
        long intervalMs = intervalSeconds * 1000;
        ScheduledTask st = new ScheduledTask(task, now + intervalMs, intervalMs);
        addTask(st);
    }

    // ----------- Internal methods ------------

    private void addTask(ScheduledTask st) {
        synchronized (lock) {
            pq.offer(st);
            lock.notify(); // Wake up worker thread
        }
    }

    private void runScheduler() {
        while (running) {
            ScheduledTask nextTask = null;

            synchronized (lock) {
                while (pq.isEmpty()) {
                    try {
                        lock.wait(); // no tasks → wait
                    } catch (InterruptedException ignored) {}
                }

                nextTask = pq.peek(); // look at earliest task

                long waitTime = nextTask.getNextExecutionTime() - System.currentTimeMillis();
                if (waitTime > 0) {
                    try {
                        lock.wait(waitTime);
                    } catch (InterruptedException ignored) {}
                    continue;
                }

                // Time to execute
                pq.poll();
            }

            // Submit to executor
            executor.submit(nextTask.getTask());

            // Reschedule if recurring
            if (nextTask.isRecurring()) {
                nextTask.updateNextExecutionTime();
                addTask(nextTask);
            }
        }
    }

    public void shutdown() {
        running = false;
        executor.shutdown();
    }
}
