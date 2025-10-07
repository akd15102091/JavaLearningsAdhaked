package Revision.LRUCache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// Optimized Read-Heavy Thread-Safe LRUCache Using ReadWriteLock
/*
 * Why is This More Efficient?
    - ✅ Allows Multiple Readers: ReadWriteLock allows multiple threads to read at the same time, reducing contention.
    - ✅ Writers Have Exclusive Access: Only when a write operation is needed does the cache lock completely, ensuring safe modifications.
    - ✅ Read-Write Lock Optimization: Upgrading from read to write lock minimizes blocking while maintaining thread safety.
 */


class LRUCache<K,V>{
    private final int capacity;
    private final ConcurrentHashMap<K,V> cache;
    private final ConcurrentLinkedDeque<K> orderQueue;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public LRUCache(int capacity){
        this.capacity = capacity;
        this.cache = new ConcurrentHashMap<>(capacity);
        this.orderQueue = new ConcurrentLinkedDeque<>();
    }

    public V get(K key){
        lock.readLock().lock(); // Allow multiple readers concurrently
        try {
            if (!cache.containsKey(key)) {
                return null;
            }
            lock.readLock().unlock(); // Release read lock early
            lock.writeLock().lock();  // Upgrade to write lock
            try {
                // Move key to the end to mark it as recently used
                orderQueue.remove(key);
                orderQueue.addLast(key);
            } finally {
                lock.writeLock().unlock();
            }
            lock.readLock().lock(); // Downgrade back to read lock
            return cache.get(key);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void put(K key, V value){
        lock.writeLock().lock();;

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
            lock.writeLock().unlock();;
        }
    }

    public void displayCache() {
        lock.readLock().lock();;
        try {
            System.out.println("Cache: " + cache);
        } finally {
            lock.readLock().unlock();
        }
    }
}

public class ReadHeavyLRUCache {
    public static void main(String[] args) {
        LRUCache<Integer, String> cache = new LRUCache<>(3);

        Runnable reader = () -> {
            // try { Thread.sleep(500); } catch (InterruptedException ignored) {}

            for (int i = 1; i <= 5; i++) {
                cache.displayCache();
                System.out.println(Thread.currentThread().getName() + " got: " + cache.get(i) +", for key: "+i );
                try { Thread.sleep(50); } catch (InterruptedException ignored) {}
            }
        };

        Runnable writer = () -> {
            for (int i = 1; i <= 5; i++) {
                cache.put(i, "Value" + i);
                System.out.println(Thread.currentThread().getName() + " inserted: " + i);
                try { Thread.sleep(50); } catch (InterruptedException ignored) {}
            }
        };

        Thread t1 = new Thread(reader, "Reader-1");
        Thread t2 = new Thread(reader, "Reader-2");
        Thread t3 = new Thread(writer, "Writer");

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
