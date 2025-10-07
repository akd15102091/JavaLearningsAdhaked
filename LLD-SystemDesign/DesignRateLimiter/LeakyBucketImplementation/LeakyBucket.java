package DesignRateLimiter.LeakyBucketImplementation;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
public class LeakyBucket {
    private final BlockingQueue<Runnable> bucket;
    private final int capacity;
    private final int leakRatePerSecond;
    private final ScheduledExecutorService schedular;

    public LeakyBucket(int capacity, int leakRatePerSecond){
        this.capacity = capacity;
        this.leakRatePerSecond = leakRatePerSecond;
        this.bucket = new LinkedBlockingQueue<>(capacity);
        this.schedular = Executors.newSingleThreadScheduledExecutor();


        long intervalMs = 1000L / leakRatePerSecond;
        schedular.scheduleAtFixedRate(() -> {
            Runnable task = bucket.poll();
            if(task != null){
                task.run();
            }
        }, 0, intervalMs, TimeUnit.MILLISECONDS) ;
    }

    public boolean submitRequest(Runnable task){
        return bucket.offer(task) ;
    }

    public void shutdown(){
        schedular.shutdown();
    }
}
