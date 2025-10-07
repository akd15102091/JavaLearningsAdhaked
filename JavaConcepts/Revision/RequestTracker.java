package Revision;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
 * - Here's a thread-safe Java implementation of a data structure that tracks the number of 
 * incoming requests grouped by attributes like IP address or browser agent over a time window. 
 * - It uses ConcurrentHashMap for thread safety and a ScheduledExecutorService to periodically clean up 
 * the expired entries.
 */
public class RequestTracker {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<Long>> requestCounts;
    private final long timeWindowMillis;
    private final ScheduledExecutorService cleaner;

    public RequestTracker(long timeWindowMillis, long cleanupIntervalMillis){
        this.timeWindowMillis = timeWindowMillis;
        this.requestCounts = new ConcurrentHashMap<>();

        this.cleaner = Executors.newSingleThreadScheduledExecutor() ;
        this.cleaner.scheduleAtFixedRate(this::cleanup, cleanupIntervalMillis, cleanupIntervalMillis, TimeUnit.MILLISECONDS) ;

    }

    public void recordRequest(String key) {
        long currentTime = System.currentTimeMillis();
        requestCounts.computeIfAbsent(key, k -> new ConcurrentLinkedQueue<>()).add(currentTime) ;
        
    }

    private void cleanup() {
        long cutoffTime = System.currentTimeMillis() - timeWindowMillis;
        for (Map.Entry<String, ConcurrentLinkedQueue<Long>> entry : requestCounts.entrySet()) {
            entry.getValue().removeIf(timestamp -> timestamp<cutoffTime) ;
            if(entry.getValue().isEmpty()){
                requestCounts.remove(entry.getKey()) ;
            }
        }
    }

    public int getRequestCount(String key){
        long cutoffTime = System.currentTimeMillis() - timeWindowMillis;

        ConcurrentLinkedQueue<Long> timestamps = requestCounts.get(key);
        if (timestamps == null) return 0;
        
        timestamps.removeIf(timestamp -> timestamp < cutoffTime);
        return timestamps.size();
    }

    public static void main(String[] args) throws InterruptedException {
        RequestTracker tracker = new RequestTracker(60000, 5000);

        tracker.recordRequest("192.168.1.1");
        tracker.recordRequest("192.168.1.1");
        tracker.recordRequest("UserAgent: Chrome");

        System.out.println("Count for 192.168.1.1: " + tracker.getRequestCount("192.168.1.1"));
        System.out.println("Count for UserAgent: Chrome: " + tracker.getRequestCount("UserAgent: Chrome"));
        
        Thread.sleep(61000);
        System.out.println("After time window expiration:");
        System.out.println("Count for 192.168.1.1: " + tracker.getRequestCount("192.168.1.1"));
    }
}
