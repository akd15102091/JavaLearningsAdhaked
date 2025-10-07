package DesignCacheLLD;

public interface DataStore<K,V> {
    void write(K key, V value);
    V read(K key);
}
