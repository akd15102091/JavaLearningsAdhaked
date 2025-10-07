package Revision.LRUCache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.locks.ReentrantLock;

class LRUCache<K,V>{
    private final int capacity;
    private final ConcurrentHashMap<K,V> cache;
    private final ConcurrentLinkedDeque<K> orderQueue;
    private final ReentrantLock lock = new ReentrantLock();

    public LRUCache(int capacity){
        this.capacity = capacity;
        this.cache = new ConcurrentHashMap<>(capacity);
        this.orderQueue = new ConcurrentLinkedDeque<>();
    }

    public V get(K key){
        lock.lock();
        try {
            if(!cache.containsKey(key)){
                return null;
            }
            // Move key to the end to mark it as recently used
            orderQueue.remove(key);
            orderQueue.addLast(key);
            return cache.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    public void put(K key, V value){
        lock.lock();

        try {
            if(cache.containsKey(key)){
                // Remove existing key from order queue
                orderQueue.remove(key);
            } 
            else if(cache.size() >= capacity){
                // Remove the least recently used item
                K lruKey = orderQueue.pollFirst();
                if (lruKey != null) {
                    cache.remove(lruKey);
                }
            }
            orderQueue.addLast(key);
            cache.put(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void displayCache() {
        lock.lock();
        try {
            System.out.println("Cache: " + cache);
        } finally {
            lock.unlock();
        }
    }
}

public class LRUCacheThreadSafe {
    public static void main(String[] args) {
        LRUCache<Integer, String> cache = new LRUCache<>(3);

        Runnable task1 = () -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Thread 1 inserted: " + i);
                cache.put(i, "Value" + i);
                try { Thread.sleep(50); } catch (InterruptedException ignored) {}
            }
        };

        Runnable task2 = () -> {
            try { Thread.sleep(100); } catch (InterruptedException ignored) {}
            for (int i = 1; i <= 5; i++) {
                System.out.println("Thread 2 got: " + cache.get(i) +", for key: "+i);
                try { Thread.sleep(200); } catch (InterruptedException ignored) {}
            }
        };

        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
