package DesignRateLimiter.DesignRateLimitedLLD;

public class Main {

    public static void handleRequest(String userId, RateLimiter rateLimiter, String requestId, Runnable request){
        if(rateLimiter.allowRequest(request)) {
            System.out.println("✅ Request allowed for user " + userId + ": " + requestId +
                    " || Using rate limiter: " + rateLimiter.getRateLimiterName());
        } else {
            System.out.println("❌ Request rejected for user " + userId + ": " + requestId +
                    " || Using rate limiter: " + rateLimiter.getRateLimiterName());
        }
    }

    public static void main(String[] args) {
        String[] users = {"user1", "user2"};

        RateLimiterFactory rateLimiterFactory = RateLimiterFactory.getInstance();

        // TOKEN_BUCKET: max 5 tokens, refill at 2 per second
        RateLimiter user1RateLimiter = rateLimiterFactory.getRateLimiter(users[0], RateLimiterType.TOKEN_BUCKET, 5, 2);

        // LEAKY_BUCKET: max queue 5, leak rate 2 per second
        RateLimiter user2RateLimiter = rateLimiterFactory.getRateLimiter(users[1], RateLimiterType.LEAKY_BUCKET, 5, 2);

        Runnable tokenBucketRequestSimulator = () -> {
            for (int i = 0; i < 20; i++) {
                // No real task for token bucket
                handleRequest(users[0], user1RateLimiter, "USER_1_REQ_" + i, () -> {});
                try {
                    Thread.sleep(200); // 200ms between requests
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Runnable leakyBucketRequestSimulator = () -> {
            for (int i = 0; i < 20; i++) {
                final int id = i;
                Runnable task = () -> {
                    System.out.println("⚙️ Processing task for USER_2_REQ_" + id);
                };
                handleRequest(users[1], user2RateLimiter, "USER_2_REQ_" + id, task);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Thread t1 = new Thread(tokenBucketRequestSimulator);
        Thread t2 = new Thread(leakyBucketRequestSimulator);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
