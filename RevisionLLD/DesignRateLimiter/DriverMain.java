package DesignRateLimiter;

import DesignRateLimiter.limiters.RateLimiter;

public class DriverMain {
    public static void processRequest(String identifier, RateLimiter rateLimiter, Runnable req){
        if(rateLimiter.allowRequest(req)){
            System.out.println("Request is ALLOWED for : "+identifier);
        }
        else{
            System.out.println("Request is REJECTED for : "+identifier);
        }
    }
    public static void main(String[] args) {
        
        RateLimiterFactory rateLimiterFactory = RateLimiterFactory.getLimiterFactory();

        RateLimiter rateLimiter1 = rateLimiterFactory.getRateLimiter("adhaked@gmail.com", RateLimiterType.FIXED_WINDOW_COUNTER, new Props(60, 1, 0, 0));
        processRequest("adhaked@gmail.com", rateLimiter1, () -> {});
        processRequest("adhaked@gmail.com", rateLimiter1, () -> {});
        processRequest("adhaked@gmail.com", rateLimiter1, () -> {});

        System.out.println("\n\n");

        RateLimiter rateLimiter2 = rateLimiterFactory.getRateLimiter("nk@gmail.com", RateLimiterType.TOKEN_BUCKET, new Props(0, 2, 1, 0));
        processRequest("nk@gmail.com", rateLimiter2, () -> {});
        processRequest("nk@gmail.com", rateLimiter2, () -> {});
        processRequest("nk@gmail.com", rateLimiter2, () -> {});
        processRequest("nk@gmail.com", rateLimiter2, () -> {});
        processRequest("nk@gmail.com", rateLimiter2, () -> {});


        RateLimiter rateLimiter3 = rateLimiterFactory.getRateLimiter("ash@gmail.com", RateLimiterType.LEAKY_BUCKET, new Props(0, 2, 0, 1));
        processRequest("ash@gmail.com", rateLimiter3, () -> {});
        processRequest("ash@gmail.com", rateLimiter3, () -> {});
        processRequest("ash@gmail.com", rateLimiter3, () -> {});
        processRequest("ash@gmail.com", rateLimiter3, () -> {});
        processRequest("ash@gmail.com", rateLimiter3, () -> {});

    }
}
