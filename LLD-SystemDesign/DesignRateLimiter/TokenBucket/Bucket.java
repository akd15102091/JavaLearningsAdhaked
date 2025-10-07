package DesignRateLimiter.TokenBucket;

import java.util.concurrent.atomic.AtomicInteger;

public class Bucket {
    private final int capacity;
    private final int refillRatePerSecond;
    private AtomicInteger tokens;
    private long lastRefillTimestamp;

    public Bucket(int capacity, int refillRatePerSecond){
        this.capacity = capacity;
        this.refillRatePerSecond = refillRatePerSecond;
        this.tokens = new AtomicInteger(capacity) ;
        this.lastRefillTimestamp = System.nanoTime();
    }

    public synchronized void refill(){
        long now = System.nanoTime();
        long elapsedTime = now - lastRefillTimestamp;
        int tokensToAdd = (int) (elapsedTime/1_000_000_000L) * refillRatePerSecond;

        if(tokensToAdd > 0){
            int newTokenCount = Math.min(capacity, tokens.get() + tokensToAdd) ;
            tokens.set(newTokenCount);
            lastRefillTimestamp = now ;
        }
    }

    public synchronized boolean allowRequest() {
        refill();
        if(tokens.get() > 0){
            tokens.decrementAndGet();
            return true;
        }
        return false;
    }
}
