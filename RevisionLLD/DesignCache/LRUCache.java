package DesignCache;

import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.locks.ReentrantLock;

@SuppressWarnings("unused")
public class LRUCache<K,V> implements Cache<K,V> {
    private Map<K, Node<K,V>> map;
    private Deque<Node<K,V>> deque;
    private int capacity; // capacity
    private ReentrantLock lock;

    public LRUCache(int capacity){
        this.capacity = capacity;
        this.map = new ConcurrentHashMap<>();
        this.deque = new ConcurrentLinkedDeque<>();
        this.lock = new ReentrantLock();
    }

    @Override
    public V get(K key) {
        this.lock.lock();
        try{
            Node<K,V> node = this.map.get(key);
            if(node == null){
                return null;
            }
            if(node.isExpired()){ // if key is expired
                this.deque.remove(node);
                this.map.remove(key);
                return null;
            }

            this.deque.remove(node);
            this.deque.addFirst(node);
            return node.getValue();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void put(K key, V value, long ttlInMillis) {
        this.lock.lock();
        try{
            Node<K,V> node = this.map.getOrDefault(key, null);

            if(node != null){ // if key does exist
                this.deque.remove(node);
                this.deque.addFirst(node);
                node.setExpiryTime(ttlInMillis);
                node.setValue(value);
                return ;
            }

            // if key does not exist
            Node<K,V> newNode = new Node<>(key, value, ttlInMillis);
            if(this.map.size() == this.capacity){ // check if capacity is full
                Node<K,V> lastNode = this.deque.removeLast();
                this.map.remove(lastNode.getKey());
            }
            this.deque.addFirst(newNode);
            this.map.put(key, newNode);
            
            return ;
        } finally {
            this.lock.unlock();
        }
    }
    
}
