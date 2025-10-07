package DesignRateLimiter.limiters;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import DesignRateLimiter.RateLimiterType;

@SuppressWarnings("unused")
public class LeakyBucketLimiter implements RateLimiter{
    private final BlockingQueue<Runnable> queue;
    private final long leakyRateInSeconds;
    private final int capacity;
    private final ScheduledExecutorService service;

    public LeakyBucketLimiter(int capacity, long leakyRateInSeconds) throws Exception{
        this.capacity = capacity;
        this.queue = new ArrayBlockingQueue<>(capacity);
        this.leakyRateInSeconds = leakyRateInSeconds;
        this.service = Executors.newSingleThreadScheduledExecutor();
        
        if(this.leakyRateInSeconds == 0){
            throw new Exception("leakyRatePerSeconds can't be zero");
        }
        long intervalMs = 1000L/this.leakyRateInSeconds;
        this.service.scheduleAtFixedRate(() -> {
            try {
                Runnable task = this.queue.poll();
                if(task != null){
                    task.run();
                }
            } catch (Exception e) {
                System.out.println("Exception occured in leaky bucket algorithm with message: "+e.getMessage());
            }
        }, 0, intervalMs, TimeUnit.MILLISECONDS);

    }

    @Override
    public boolean allowRequest(Runnable task) {
        return this.queue.offer(task); // return false, if queue is full
    }

    @Override
    public RateLimiterType getRateLimiterName() {
        return RateLimiterType.LEAKY_BUCKET;
    }

    @Override
    public boolean allowRequest() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'allowRequest'");
    }
    
}
