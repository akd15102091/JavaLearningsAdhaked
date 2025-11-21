package DesignTrainBookingSystem;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private static final AtomicInteger seq = new AtomicInteger(1000);
    static String nextId(String prefix) { return prefix + seq.getAndIncrement(); }
}
