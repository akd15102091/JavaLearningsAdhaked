package DesignRateLimiter.limiters;

import java.util.concurrent.atomic.AtomicInteger;

import DesignRateLimiter.RateLimiterType;

public class FixedWindowLimiter implements RateLimiter{
    private AtomicInteger counter;
    private long currentBucketStartTimestamp;
    private int bucketLengthInSec;
    private int limit;

    public FixedWindowLimiter(int bucketLengthInSec, int limit){
        this.counter = new AtomicInteger(0);
        this.currentBucketStartTimestamp = System.currentTimeMillis();
        this.bucketLengthInSec = bucketLengthInSec;
        this.limit = limit;
    }

    @Override
    public synchronized boolean allowRequest() {
        long currentTime = System.currentTimeMillis();
        
        // check current bucket is feasible or new bucket need to introduce
        long diffInMillis = currentTime - currentBucketStartTimestamp ; 
        if(diffInMillis/1000 >= bucketLengthInSec){ // introduce new bucket timestamp, reset count
            this.currentBucketStartTimestamp = currentTime;
            this.counter.set(0);
        }

        if(this.counter.get() < limit){
            this.counter.incrementAndGet();
            return true;
        }
    
        return false;
    }

    @Override
    public RateLimiterType getRateLimiterName() {
        return RateLimiterType.FIXED_WINDOW_COUNTER;
    }

    public String getInfo() {
        return "FixedWindowLimiter [counter=" + counter.get() + ", currentBucketStartTimestamp=" + currentBucketStartTimestamp
                + ", bucketLengthInSec=" + bucketLengthInSec + ", limit=" + limit + "]";
    }

    
    
}
