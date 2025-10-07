package DesignRateLimiter.LeakyBucketImplementation;

import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unused")
public class LeakyBucketManager {
    private final int defaultCapacity;
    private final int defaultLeakRate;
    private final ConcurrentHashMap<String, LeakyBucket> userBuckets;

    public LeakyBucketManager(int defaultCapacity, int defaultLeakRate){
        this.defaultCapacity = defaultCapacity;
        this.defaultLeakRate = defaultLeakRate;
        this.userBuckets = new ConcurrentHashMap<>();
    }

    public boolean allowRequest(String userId, Runnable task){
        LeakyBucket bucket = userBuckets.computeIfAbsent(userId, id -> new LeakyBucket(defaultCapacity, defaultLeakRate)) ;
        return bucket.submitRequest(task) ;
    }

    public void shutdownAll(){
        for(LeakyBucket bucket : userBuckets.values()){
            bucket.shutdown();
        }
    }
}
