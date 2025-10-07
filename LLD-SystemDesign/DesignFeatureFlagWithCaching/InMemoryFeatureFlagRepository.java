package DesignFeatureFlagWithCaching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryFeatureFlagRepository {
    private final Map<String, FeatureFlag> db = new HashMap<>();

    public synchronized void save(FeatureFlag flag) {
        db.put(flag.getKey(), flag);
    }

    public synchronized FeatureFlag findByKey(String key) {
        return db.get(key);
    }

    public synchronized void delete(String key) {
        db.remove(key);
    }

    public synchronized List<FeatureFlag> findAll() {
        return new ArrayList<>(db.values());
    }
}
