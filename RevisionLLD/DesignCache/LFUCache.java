package DesignCache;

import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.locks.ReentrantLock;

/*
 * Eviction: remove Least frequent node, if there are multiple node, remove least recent used node out of them
 */
@SuppressWarnings("unused")
public class LFUCache<K,V> implements Cache<K,V>{
    private int capacity;
    private int minFrequency;
    private Map<Integer, Deque<Node<K,V>>> freqMap ; // frequency, List of keys whose frequency is this
    private Map<K, Node<K,V>> cache;
    private ReentrantLock lock;

    public LFUCache(int capacity){
        this.capacity = capacity;
        this.minFrequency = 0;
        this.freqMap = new ConcurrentHashMap<>();
        this.cache = new ConcurrentHashMap<>();
        this.lock = new ReentrantLock();
    }

    public void updateNodeStatusInCache(Node<K,V> node){
        // remove it from current doubly list, where it currently lies
        Deque<Node<K,V>> deque = this.freqMap.get(node.getFrequency());
        deque.remove(node);
        if(deque.size() == 0 && this.minFrequency == node.getFrequency()){
            this.minFrequency++;
        }

        // update frequency for this node and add this node to new frequency key's value
        int newFrequency = node.getFrequency()+1 ;
        node.setFrequency(newFrequency);
        Deque<Node<K,V>> dequeList = this.freqMap.getOrDefault(newFrequency, new ConcurrentLinkedDeque<>());
        dequeList.addFirst(node);
        this.freqMap.put(newFrequency, dequeList);
    }

    @Override
    public V get(K key) {
        this.lock.lock();
        try {
            Node<K,V> node = this.cache.getOrDefault(key, null);
            if(node == null) return null;
            if(node.isExpired()){
                // remove node from freqMap, List, update frequency
                int freq = node.getFrequency();
                this.cache.remove(key);
                Deque<Node<K,V>> dq = this.freqMap.get(freq);
                dq.remove(node);
                if(dq.size() == 0 && this.minFrequency == freq) {
                    this.minFrequency +=1;
                }
                return null;
            }

            this.updateNodeStatusInCache(node);
            return node.getValue();

        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void put(K key, V value, long ttlInMillis) {
        this.lock.lock();
        try {
            Node<K,V> node = this.cache.getOrDefault(key, null);
            if(node != null){ // existing entry
                node.setExpiryTime(ttlInMillis);
                node.setValue(value);
                this.updateNodeStatusInCache(node);
                return;
            }

            // check if capacity is full, then evict
            if(this.cache.size() == capacity){
                Deque<Node<K,V>> dq = this.freqMap.get(this.minFrequency);
                Node<K,V> nodeToEvict = dq.removeLast();
                this.cache.remove(nodeToEvict.getKey());
                if(dq.size() == 0){
                    this.freqMap.remove(this.minFrequency); // cleanup
                }

            }
            Node<K,V> newNodeToAdd = new Node<K,V>(key, value, ttlInMillis,1);
            Deque<Node<K,V>> dq = this.freqMap.getOrDefault(1, new ConcurrentLinkedDeque<>());
            dq.addFirst(newNodeToAdd);
            this.freqMap.put(1, dq);
            this.minFrequency = 1;
            this.cache.put(key, newNodeToAdd);
        } finally {
            this.lock.unlock();
        }
    }

    
}
