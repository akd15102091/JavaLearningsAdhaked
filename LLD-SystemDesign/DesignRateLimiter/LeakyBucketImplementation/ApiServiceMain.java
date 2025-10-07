package DesignRateLimiter.LeakyBucketImplementation;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
public class ApiServiceMain {
    private static final LeakyBucketManager bucketManager = new LeakyBucketManager(5, 3); // capacity=1, leakRate=1/sec
    private static final ConcurrentHashMap<String, UserStats> userStatsMap = new ConcurrentHashMap<>();

    public static void handleRequest(String userId, String requestId) {
        boolean accepted = bucketManager.allowRequest(userId, () -> {
            System.out.println(Thread.currentThread().getName() + " -> Processed for user " + userId + ": " + requestId);
        });

        // Track stats
        UserStats stats = userStatsMap.computeIfAbsent(userId, k -> new UserStats());
        if (accepted) {
            stats.accepted.incrementAndGet();
        } else {
            stats.rejected.incrementAndGet();
            System.out.println(Thread.currentThread().getName() + " -> Rate limit exceeded for user " + userId + ": " + requestId);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String[] users = {"userA", "userB"};
        int threads = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threads);
    
        // Each user sends 20 burst requests
        for (String user : users) {
            for (int i = 0; i < 20; i++) {
                final int requestId = i;
                final String currentUser = user;
                executor.submit(() -> {
                    handleRequest(currentUser, "REQ_" + requestId);
                    try {
                        Thread.sleep(1000) ;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    
        // Wait for processing
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        bucketManager.shutdownAll();
    
        // Print stats
        System.out.println("\n========= USER STATS =========");
        for (var entry : userStatsMap.entrySet()) {
            String userId = entry.getKey();
            UserStats stats = entry.getValue();
            System.out.println("User: " + userId + " | Accepted: " + stats.accepted + " | Rejected: " + stats.rejected);
        }
    }
    
}