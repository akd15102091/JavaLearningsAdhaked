package DesignRateLimiter;

public class Props {
    private int bucketLengthInSec;

    private int refillRatePerSecond;
    private int capacity;

    private long leakyRateInSeconds;
    
    public Props(int bucketLengthInSec, int capacity, int refillRatePerSecond, long leakyRateInSeconds){
        this.bucketLengthInSec = bucketLengthInSec;
        this.capacity = capacity;
        this.refillRatePerSecond = refillRatePerSecond;
        this.leakyRateInSeconds = leakyRateInSeconds;
    }

    public int getBucketLengthInSec() {
        return bucketLengthInSec;
    }

    public int getRefillRatePerSecond() {
        return refillRatePerSecond;
    }

    public int getCapacity() {
        return capacity;
    }

    public long getLeakyRateInSeconds() {
        return leakyRateInSeconds;
    }

    
}
