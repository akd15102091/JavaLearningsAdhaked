package Revision.LRUCache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/*
 * Note: There are below issue with this implementation. Check another code in same folder
 * 
 * Your LRUCache implementation uses a ReentrantLock to synchronize get(), put(), and displayCache() operations. However, this implementation is not fully thread-safe due to potential race conditions in LinkedHashMap. Here’s why:

    Problems in the Current Implementation:
        - Concurrent Modification Issues with LinkedHashMap:
        - Even though you're using ReentrantLock, LinkedHashMap is not inherently thread-safe.
        - If multiple threads modify it simultaneously, it may lead to undefined behavior.
        - The removeEldestEntry() method may be invoked unexpectedly in a concurrent scenario, leading to corruption.

    Lack of Atomicity in get():
        Since LinkedHashMap is access-order based, calling get() moves the accessed entry to the end of the map.
        If another thread is modifying the map while this happens, it can cause race conditions or concurrent modification issues.
 */

class LRUCache<K,V>{
    private final int capacity;
    private final Map<K,V> cache;
    private final ReentrantLock lock = new ReentrantLock();
    
    public LRUCache(int capacity){
        this.capacity = capacity;
        this.cache = new LinkedHashMap<K,V>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K,V> entry){
                return size()> LRUCache.this.capacity;
            }
        };
    }

    public V get(K key){
        lock.lock();
        try {
            return cache.getOrDefault(key, null);
        } finally {
            lock.unlock();
        }
    }

    public void put(K key, V value){
        lock.lock();
        try {
            cache.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    public void displayCache(){
        lock.lock();
        try {
            System.out.println(cache);
        } finally {
            lock.unlock();
        }
    }


}

public class LRUCacheTest {
    public static void main(String[] args) {
        LRUCache<Integer, String> cache = new LRUCache<>(3) ;
        Runnable task1 = ()->{
            for(int i=1;i <=5; i++){
                cache.put(i, "Value" + i);
                System.out.println("Thread 1 inserted: " + i);
                try { Thread.sleep(50); } catch (InterruptedException ignored) {}
            }
        };

        Runnable task2 = () -> {
            try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

            for (int i = 1; i <= 5; i++) {
                System.out.println("Thread 2 got: " + cache.get(i) +" for key: "+i);
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
