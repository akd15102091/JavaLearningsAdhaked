package DesignRateLimiter.LeakyBucketImplementation;

import java.util.concurrent.atomic.AtomicInteger;

public class UserStats {
    public AtomicInteger accepted = new AtomicInteger(0);
    public AtomicInteger rejected = new AtomicInteger(0);
}
