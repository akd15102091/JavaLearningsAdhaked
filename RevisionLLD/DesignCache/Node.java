package DesignCache;

@SuppressWarnings("unused")
public class Node<K, V> {
    private K key;
    private V value;
    private long expiryTime;
    private int frequency; // for LFU cache

    public Node(K key, V value, long ttlInMillis) {
        this.key = key;
        this.value = value;
        this.expiryTime = System.currentTimeMillis() + ttlInMillis;
    }

    public Node(K key, V value, long ttlInMillis, int frequency) {
        this.key = key;
        this.value = value;
        this.expiryTime = System.currentTimeMillis() + ttlInMillis;
        this.frequency = frequency;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }

    public K getKey(){
        return this.key;
    }

    public V getValue(){
        return this.value;
    }

    public int getFrequency(){
        return this.frequency;
    }

    public void setExpiryTime(long expiryTime){
        this.expiryTime = expiryTime;
    }

    public void setValue(V value){
        this.value = value;
    }

    public void setFrequency(int freq){
        this.frequency = freq;
    }
}
