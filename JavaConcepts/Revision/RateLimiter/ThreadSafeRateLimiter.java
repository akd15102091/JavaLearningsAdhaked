package Revision.RateLimiter;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * Question: Design and implement a thread-safe rate limiter that allows up to N requests per T seconds 
            from any number of threads.

💡  Follow-up: How would you ensure high throughput and minimal locking?
 */

 /*
  * 
  ✅ Approach: Token Bucket Algorithm (High Throughput)
        The token bucket algorithm is ideal for high-throughput systems:
        Tokens are added at a fixed rate.
        Requests consume tokens.
        If no tokens are available, the request is rejected.

    
    How it Ensures High Throughput
        - Minimal synchronization: only in the refill() method, and only if tokens need updating.
        - Lock-free token consumption via AtomicInteger.compareAndSet.
        - Volatile timestamp for safe reads across threads.
  */

@SuppressWarnings("unused")
public class ThreadSafeRateLimiter {

    private final int maxTokens;
    private final long refillIntervalMillis;
    private final int refillRate;

    private AtomicInteger availableTokens;
    private volatile long lastRefillTimestamp;

    public ThreadSafeRateLimiter(int maxTokens, int refillRatePerSecond) {
        this.maxTokens = maxTokens;
        this.refillRate = refillRatePerSecond;
        this.refillIntervalMillis = 1000L;
        this.availableTokens = new AtomicInteger(maxTokens);
        this.lastRefillTimestamp = System.currentTimeMillis();
    }

    public boolean tryAcquire() {
        refill();

        while (true) {
            int currentTokens = availableTokens.get();
            if (currentTokens == 0) {
                return false;
            }
            if (availableTokens.compareAndSet(currentTokens, currentTokens - 1)) {
                return true;
            }
        }
    }

    private void refill() {
        long now = System.currentTimeMillis();
        long elapsed = now - lastRefillTimestamp;

        if (elapsed > 0) {
            int tokensToAdd = (int) (elapsed * refillRate / 1000);
            if (tokensToAdd > 0) {
                synchronized (this) {
                    if ((now - lastRefillTimestamp) > 0) {
                        int updatedTokens = Math.min(maxTokens, availableTokens.get() + tokensToAdd);
                        availableTokens.set(updatedTokens);
                        lastRefillTimestamp = now;
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

