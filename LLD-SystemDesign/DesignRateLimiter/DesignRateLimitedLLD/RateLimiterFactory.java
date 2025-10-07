package DesignRateLimiter.DesignRateLimitedLLD;

import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unused")
public class RateLimiterFactory {
    private final ConcurrentHashMap<String, RateLimiter> limiterMap = new ConcurrentHashMap<>();
    private static RateLimiterFactory instance;

    private RateLimiterFactory(){}

    public static synchronized RateLimiterFactory getInstance(){
        if(instance == null){
            instance = new RateLimiterFactory();
        }
        return instance;
    }

    public RateLimiter getRateLimiter(String userId, RateLimiterType rateLimiterType, int capacity, int refillRate) {
        return limiterMap.computeIfAbsent(userId, k -> createRateLimiter(rateLimiterType, capacity, refillRate)) ;
    }

    public RateLimiter createRateLimiter(RateLimiterType rateLimiterType, int capacity, int refillRate){
        RateLimiter rateLimiter;

        switch (rateLimiterType) {
            case TOKEN_BUCKET:
                rateLimiter = new TokenBucketRateLimiter(capacity, refillRate);
                break;

            case LEAKY_BUCKET:
                rateLimiter = new LeakyBucketRateLimiter(capacity, refillRate);
                break;
        
            default:
                rateLimiter = new TokenBucketRateLimiter(capacity, refillRate);
                break;
        }

        return rateLimiter;
    }
}
