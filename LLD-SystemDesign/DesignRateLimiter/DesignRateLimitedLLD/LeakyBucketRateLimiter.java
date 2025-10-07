package DesignRateLimiter.DesignRateLimitedLLD;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
public class LeakyBucketRateLimiter implements RateLimiter{
    private final BlockingQueue<Runnable> bucket;
    private final int capacity;
    private final int leakRatePerSecond;
    private final ScheduledExecutorService executer;

    public LeakyBucketRateLimiter(int capacity, int leakRatePerSecond){
        this.capacity = capacity;
        this.leakRatePerSecond = leakRatePerSecond;
        this.bucket = new ArrayBlockingQueue<>(capacity);

        long intervalMs = 1000L / leakRatePerSecond;

        this.executer = Executors.newSingleThreadScheduledExecutor();
        this.executer.scheduleAtFixedRate(() -> {
            Runnable task = this.bucket.poll();
            if(task != null){
                task.run();
            }
        }, 0, intervalMs , TimeUnit.MILLISECONDS) ;
    }

    @Override
    public String getRateLimiterName() {
        return "LEAKY_BUCKET";
    }

    @Override
    public boolean allowRequest(Runnable task) {
        return this.bucket.offer(task) ;// return false, if not able to put task in queue
    }
}
