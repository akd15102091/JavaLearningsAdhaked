package DesignRateLimiter.DesignRateLimitedLLD;

public interface RateLimiter {
    public String getRateLimiterName() ;
    public boolean allowRequest(Runnable task) ;
}
