package DesignCacheLLD;

import java.util.HashMap;
import java.util.Map;

public class InMemoryDB<K,V> implements DataStore<K,V>{
    private final Map<K,V> db = new HashMap<>();

    @Override
    public void write(K key, V value) {
        db.put(key, value);
    }

    @Override
    public V read(K key) {
       return db.get(key);
    }
    
}
