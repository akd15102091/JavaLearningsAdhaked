package DesignRateLimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import DesignRateLimiter.limiters.FixedWindowLimiter;
import DesignRateLimiter.limiters.LeakyBucketLimiter;
import DesignRateLimiter.limiters.RateLimiter;
import DesignRateLimiter.limiters.TokenBucketLimiter;

public class RateLimiterFactory {
    private static RateLimiterFactory instance;
    private Map<String, RateLimiter> limitersMap; // key- identifier(userId, apiKey, IP)

    private RateLimiterFactory(){
        this.limitersMap = new ConcurrentHashMap<>();
    }

    public synchronized static RateLimiterFactory getLimiterFactory(){
        if(instance == null){
            instance = new RateLimiterFactory();
        }
        return instance;
    }   

    public RateLimiter getRateLimiter(String identifier, RateLimiterType rateLimiterType, Props props){
        return this.limitersMap.computeIfAbsent(identifier, k-> {
            try {
                return this.createRateLimiter(rateLimiterType, props);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public RateLimiter createRateLimiter(RateLimiterType rateLimiterType, Props props) throws Exception{
        RateLimiter rateLimiter = null;

        switch (rateLimiterType) {
            case FIXED_WINDOW_COUNTER:
                rateLimiter = new FixedWindowLimiter(props.getBucketLengthInSec(), props.getCapacity());
                break;

            case TOKEN_BUCKET:
                rateLimiter = new TokenBucketLimiter(props.getCapacity(), props.getRefillRatePerSecond());
                break;

            case LEAKY_BUCKET:
                rateLimiter = new LeakyBucketLimiter(props.getCapacity(), props.getLeakyRateInSeconds());
                break;
        
            default:
                rateLimiter = new FixedWindowLimiter(props.getBucketLengthInSec(), props.getCapacity());
                break;
        }

        return rateLimiter ;
    }
}
