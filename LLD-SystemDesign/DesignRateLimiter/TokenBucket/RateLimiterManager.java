package DesignRateLimiter.TokenBucket;

import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unused")
public class RateLimiterManager {
    private final int defaultCapacity;
    private final int defaultRefillRate;
    private final ConcurrentHashMap<String, Bucket> userBuckets ;

    public RateLimiterManager(int defaultCapacity, int defaultRefillRate){
        this.defaultCapacity = defaultCapacity;
        this.defaultRefillRate = defaultRefillRate;
        userBuckets = new ConcurrentHashMap<>();
    }

    public boolean allowRequest(String userId){
        Bucket tokenBucket = userBuckets.computeIfAbsent(userId, id -> new Bucket(defaultCapacity, defaultRefillRate));
        return tokenBucket.allowRequest();
    }
}
