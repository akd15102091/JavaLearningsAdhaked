package DesignRateLimiter.limiters;

import java.util.concurrent.atomic.AtomicInteger;

import DesignRateLimiter.RateLimiterType;

@SuppressWarnings("unused")
public class TokenBucketLimiter implements RateLimiter{
    private int capacity;
    private int refillRatePerSecond;
    private long lastRefillTimestamp;
    private AtomicInteger tokens;

    public TokenBucketLimiter(int capacity, int refillRatePerSecond){
        this.capacity = capacity;
        this.tokens = new AtomicInteger(capacity);
        this.refillRatePerSecond = refillRatePerSecond;
        this.lastRefillTimestamp = System.currentTimeMillis();
    }

    public void refillBucket(){
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - this.lastRefillTimestamp ;

        if (elapsedTime <= 0) return;

        int tokensToAdd = (int)((elapsedTime / 1000.0) * refillRatePerSecond);
        if (tokensToAdd > 0) {
            int newTokens = Math.min(capacity, tokens.get() + tokensToAdd);
            tokens.set(newTokens);
            lastRefillTimestamp = currentTime;
        }
    }

    @Override
    public synchronized boolean allowRequest() {
        refillBucket();
        if(this.tokens.get() > 0){
            this.tokens.decrementAndGet();
            return true;
        }

        return false;
    }

    @Override
    public RateLimiterType getRateLimiterName() {
        return RateLimiterType.TOKEN_BUCKET;
    }
    
}
