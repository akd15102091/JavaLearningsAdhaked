package Revision_Multithreading.RateLimiter;

import java.util.concurrent.atomic.AtomicInteger;

/*
 * Question: Design and implement a thread-safe rate limiter that allows up to N requests per T seconds 
            from any number of threads.

💡  Follow-up: How would you ensure high throughput and minimal locking?
 */

@SuppressWarnings("unused")
public class ThreadSafeRateLimiter {
    private int maxTokens;
    private int refillRate;
    private long refillTimeInterval;
    private AtomicInteger availableTokens;
    private volatile long lastRefilledTimestamp;

    public ThreadSafeRateLimiter(int maxTokens, int refillRatePerSecond){
        this.maxTokens = maxTokens;
        this.refillRate = refillRatePerSecond;
        this.refillTimeInterval = 1000L;
        this.availableTokens = new AtomicInteger(maxTokens);
        this.lastRefilledTimestamp = System.currentTimeMillis();
    }

    public boolean tryAcquire(){
        refill();

        while (true) {
            int currentTokens = this.availableTokens.get();
            if(currentTokens==0) return false;

            if(availableTokens.compareAndSet(currentTokens, currentTokens-1)){
                return true;
            }
        }

    }

    public void refill(){
        long now = System.currentTimeMillis();
        long elapsed = now - this.lastRefilledTimestamp;

        if (elapsed > 0) {
            int tokensToAdd = (int) (elapsed * refillRate / 1000);
            if (tokensToAdd > 0) {
                synchronized (this) {
                    if (elapsed > 0) {
                        int updatedTokens = Math.min(maxTokens, availableTokens.get() + tokensToAdd);
                        availableTokens.set(updatedTokens);
                        this.lastRefilledTimestamp = now;
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        //allow up to 10 requests max, refilling 5 tokens per second
        int maxRequests = 10;
        int refillRatePerSecond = 5;
        ThreadSafeRateLimiter limiter = new ThreadSafeRateLimiter(maxRequests, refillRatePerSecond);

        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            for (int i = 0; i < 5; i++) {
                boolean allowed = limiter.tryAcquire();
                System.out.println(threadName + " - request " + (i + 1) + " - " + (allowed ? "allowed ✅" : "rate limited ❌"));
                try {
                    Thread.sleep(200); // simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        int numberOfThreads = 6;
        for (int i = 0; i < numberOfThreads; i++) {
            new Thread(task, "Thread-" + i).start();
        }
    }

    
}
