package DesignFeatureFlagWithCaching;

import java.util.List;

public class FeatureFlagService {
    private final InMemoryFeatureFlagRepository repository;
    private final FeatureFlagCache cache;

    public FeatureFlagService(InMemoryFeatureFlagRepository repository) {
        this.repository = repository;
        this.cache = new FeatureFlagCache();
    }

    // ✅ Get flag considering userId and environment
    public FeatureFlag getFeatureFlag(String name, String userId, String environment){
        String key = userId+":"+environment;
        FeatureFlag flag = this.cache.get(key) ;

        if(flag == null){
            synchronized(this){
                flag = cache.get(key);
                if (flag == null) {
                    flag = repository.findByKey(key);
                    if (flag != null) cache.put(key, flag);
                }
            }
        }

        if(flag == null) return null;
        
        // Check user-specific override
        if (flag.getTargetedUsers().contains(userId)) return flag;

        return flag.isEnabled() ? flag : null;
    }

    // ✅ Create a new flag
    public synchronized void createFeatureFlag(FeatureFlag flag) {
        if (repository.findByKey(flag.getKey()) != null)
            throw new IllegalArgumentException("Flag already exists for environment");

        repository.save(flag);
        cache.put(flag.getKey(), flag);
    }

    // ✅ Update an existing flag
    public synchronized void updateFeatureFlag(FeatureFlag updatedFlag) {
        repository.save(updatedFlag);
        cache.put(updatedFlag.getKey(), updatedFlag);
    }

    // ✅ Delete a flag
    public synchronized void deleteFeatureFlag(String name, String environment) {
        String key = name + ":" + environment;
        repository.delete(key);
        cache.invalidate(key);
    }

    public List<FeatureFlag> listAllFlags() {
        return repository.findAll();
    }
    
}
