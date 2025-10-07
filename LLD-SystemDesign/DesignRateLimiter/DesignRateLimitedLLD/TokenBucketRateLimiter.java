package DesignRateLimiter.DesignRateLimitedLLD;

import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("unused")
public class TokenBucketRateLimiter implements RateLimiter{
    private final int capacity ;
    private final int refillRatePerSecond;
    private long lastRefillTimestamp;
    private AtomicInteger tokens;

    public TokenBucketRateLimiter(int capacity, int refillRatePerSecond){
        this.capacity = capacity;
        this.refillRatePerSecond = refillRatePerSecond;
        this.tokens = new AtomicInteger(capacity) ;
        this.lastRefillTimestamp = System.nanoTime();
    }

    public synchronized void refill(){
        long now = System.nanoTime();
        long elapsedTime = now - lastRefillTimestamp;
        int tokensToAdd = (int)((elapsedTime * this.refillRatePerSecond)/1_000_000_000) ;

        if(tokensToAdd > 0){
            int newTokens = Math.min(this.capacity, this.tokens.get() + tokensToAdd) ;
            this.tokens.set(newTokens);
            this.lastRefillTimestamp = now;
        }
    }

    public synchronized boolean allowRequest(Runnable task){
        this.refill();
        if(this.tokens.get() > 0){
            tokens.decrementAndGet();
            return true;
        }
        return false;
    }

    public String getRateLimiterName(){
        return "TOKEN_BUCKET" ;
    }
}
