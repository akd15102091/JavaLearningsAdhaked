package DesignFeatureFlagWithCaching;

import java.util.concurrent.ConcurrentHashMap;

public class FeatureFlagCache {
    private final ConcurrentHashMap<String, FeatureFlag> cache = new ConcurrentHashMap<>();

    public FeatureFlag get(String key) {
        return cache.get(key);
    }

    public void put(String key, FeatureFlag flag) {
        cache.put(key, flag);
    }

    public void invalidate(String key) {
        cache.remove(key);
    }

    public void invalidateAll() {
        cache.clear();
    }
}
