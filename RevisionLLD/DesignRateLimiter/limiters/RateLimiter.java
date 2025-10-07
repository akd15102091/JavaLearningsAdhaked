package DesignRateLimiter.limiters;

import DesignRateLimiter.RateLimiterType;

public interface RateLimiter {
    /*
     *  - Algorithms that don’t need the task just use the first method.
        - Leaky Bucket overrides the second method to enqueue tasks.
        - This keeps flexibility but keeps the primary contract simple.
     */
    public boolean allowRequest();
    default boolean allowRequest(Runnable task) {
        return allowRequest();
    }

    public RateLimiterType getRateLimiterName();
} 
